/*
 * UpcomingMCU API
 * Copyright (C) 2024 The UpcomingMCU Project Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package app.umcu.api.remote

import app.umcu.api.remote.model.TmdbList
import app.umcu.api.remote.model.TmdbListItem
import app.umcu.api.remote.model.TmdbMovieDetails
import app.umcu.api.remote.model.TmdbSeriesDetails
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.util.UriComponentsBuilder

@Service
class Tmdb(
	@Value("\${tmdb.api-key}") private val apiKey: String,
	@Value("\${tmdb.list}") private val listId: String,
	@Value("\${tmdb.base-url}") private val baseUrl: String,
) {

	private val restTemplate: RestTemplate = RestTemplate()
	private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

	private inline fun <reified T : Any> request(
		pathSegments: Array<String> = emptyArray(),
		params: List<Pair<String, Any>> = emptyList(),
	): T {
		val headers = HttpHeaders()
		headers.setBearerAuth(apiKey)
		val httpEntity = HttpEntity("body", headers)

		val uriBuilder = UriComponentsBuilder.fromUriString(baseUrl).pathSegment(*pathSegments)
		params.forEach { uriBuilder.queryParam(it.first, it.second) }
		val uri = uriBuilder.build().toUri()

		logger.info("Request to '$uri'")

		return try {
			val response = restTemplate.exchange<T>(uri, HttpMethod.GET, httpEntity)
			logger.info("Response status ${response.statusCode}")
			response.body ?: throw NullPointerException("Response body was null for '$uri'.")
		} catch (e: Exception) {
			throw RuntimeException("Could not fetch resource from '$uri': \"${e.message ?: "Unknown error."}\"")
		}
	}

	private fun getListPage(listId: String, page: Int = 1): TmdbList {
		val list = request<TmdbList>(pathSegments = arrayOf("list", listId), params = listOf(Pair("page", page)))
		return list
	}

	fun getList(): List<TmdbListItem> {
		val items = mutableListOf<TmdbListItem>()
		var currentPage = 1
		do {
			val list = getListPage(listId, currentPage)
			items.addAll(list.items)
			currentPage++
		} while (currentPage < list.totalPages)
		return items.toList()
	}

	fun getMovie(movieId: Int): TmdbMovieDetails {
		return request<TmdbMovieDetails>(
			pathSegments = arrayOf("movie", movieId.toString()),
			params = listOf(Pair("append_to_response", "release_dates"))
		)
	}

	fun getSeries(seriesId: Int): TmdbSeriesDetails {
		return request<TmdbSeriesDetails>(
			pathSegments = arrayOf("tv", seriesId.toString()),
			params = listOf(Pair("append_to_response", "external_ids"))
		)
	}
}
