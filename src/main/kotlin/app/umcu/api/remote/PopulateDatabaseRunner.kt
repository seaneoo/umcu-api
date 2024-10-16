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

import app.umcu.api.productions.ProductionDocument
import app.umcu.api.productions.ProductionRepository
import app.umcu.api.remote.model.TmdbMovieDetails
import app.umcu.api.remote.model.TmdbSeriesDetails
import app.umcu.api.remote.model.getReleaseDate
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PopulateDatabaseRunner(
	private val tmdbApiService: TmdbApiService,
	private val productionRepository: ProductionRepository,
) : CommandLineRunner {

	private val logger = KotlinLogging.logger {}

	@Transactional
	override fun run(vararg args: String?) {
		val list = tmdbApiService.getList()
		val movies = list.filter { it.mediaType == "movie" }.map { tmdbApiService.getMovie(it.tmdbId) }
		val series = list.filter { it.mediaType == "tv" }.map { tmdbApiService.getSeries(it.tmdbId) }
		populateDatabase(movies, series)
	}

	@Transactional
	fun populateDatabase(movies: List<TmdbMovieDetails>, series: List<TmdbSeriesDetails>) {
		try {
			productionRepository.deleteAll()

			val movieProductionDocuments = movies.map {
				ProductionDocument(
					tmdbId = it.tmdbId,
					imdbId = it.imdbId,
					title = it.title,
					releaseDate = it.getReleaseDate()?.releaseDate?.split("T")?.first(),
					posterPath = it.posterPath,
					overview = it.overview
				)
			}

			productionRepository.saveAll(movieProductionDocuments)

			val seriesProductionDocuments = series.flatMap {
				it.seasons.map { season ->
					val title = if (it.seasonCount > 1) "${it.name} Season ${season.seasonNumber}" else it.name
					val posterPath = if (it.seasonCount > 1) season.posterPath else it.posterPath
					val overview = if (it.seasonCount > 1) season.overview else it.overview
					ProductionDocument(
						tmdbId = it.tmdbId,
						imdbId = it.externalIds.imdbId,
						title = title,
						releaseDate = season.airDate,
						posterPath = posterPath,
						overview = overview
					)
				}
			}

			productionRepository.saveAll(seriesProductionDocuments)
		} catch (e: Exception) {
			logger.warn { "Could not populate database: \"${e.message ?: "Unknown error"}\"." }
		}
	}
}
