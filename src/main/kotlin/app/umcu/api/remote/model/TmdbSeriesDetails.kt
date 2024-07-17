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

data class TmdbSeriesDetails(
	@JsonProperty("id") val tmdbId: Int,
	@JsonProperty("number_of_episodes") val episodeCount: Int,
	@JsonProperty("number_of_seasons") val seasonCount: Int,
	val name: String,
	val overview: String?,
	@JsonProperty("poster_path") val posterPath: String?,
	val seasons: List<TmdbSeriesSeason>,
	@JsonProperty("external_ids") val externalIds: TmdbSeriesExternalIds,
)

data class TmdbSeriesSeason(
	@JsonProperty("air_date") val airDate: String?,
	@JsonProperty("episode_count") val episodeCount: Int,
	@JsonProperty("id") val tmdbId: Int,
	val name: String,
	val overview: String?,
	@JsonProperty("poster_path") val posterPath: String?,
	@JsonProperty("season_number") val seasonNumber: Int,
)

data class TmdbSeriesExternalIds(@JsonProperty("imdb_id") val imdbId: String?)
