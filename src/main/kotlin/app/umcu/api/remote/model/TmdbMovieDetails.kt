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

package app.umcu.api.remote.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.slf4j.LoggerFactory

data class TmdbMovieDetails(
	@JsonProperty("id") val tmdbId: Int,
	@JsonProperty("imdb_id") val imdbId: String?,
	val title: String,
	val overview: String?,
	@JsonProperty("poster_path") val posterPath: String?,
	@JsonProperty("release_dates") val releaseDates: TmdbMovieReleaseDateResults,
)

data class TmdbMovieReleaseDateResults(val results: List<TmdbMovieReleaseDate>)

data class TmdbMovieReleaseDate(
	@JsonProperty("iso_3166_1") val iso31661: String,
	@JsonProperty("release_dates") val releaseDates: List<TmdbMovieReleaseDates>,
)

data class TmdbMovieReleaseDates(
	val certification: String,
	@JsonProperty("release_date") val releaseDate: String,
	val type: Int,
)

fun TmdbMovieDetails.getReleaseDate(): TmdbMovieReleaseDates? {
	val logger = LoggerFactory.getLogger(TmdbMovieDetails::class.java)

	val usReleaseDate = this.releaseDates.results.firstOrNull { it.iso31661 == "US" }?.releaseDates

	if (usReleaseDate == null) {
		logger.warn("US release date not found for '${this.tmdbId}'.")
		return null
	}

	val releaseDate = usReleaseDate.firstOrNull { it.type == 3 } ?: usReleaseDate.firstOrNull { it.type == 4 }
	?: usReleaseDate.firstOrNull { it.type == 5 }

	if (releaseDate == null) logger.warn("Theatrical, digital, or physical release date not found for '${this.tmdbId}'.")
	return releaseDate
}
