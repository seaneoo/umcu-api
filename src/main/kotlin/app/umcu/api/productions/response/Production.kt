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

package app.umcu.api.productions.response

import app.umcu.api.productions.ProductionDocument
import com.fasterxml.jackson.annotation.JsonProperty

data class Production(
	val slug: String,
	@JsonProperty("tmdb_id") val tmdbId: Int,
	@JsonProperty("imdb_id") val imdbId: String? = null,
	val title: String,
	@JsonProperty("release_date") val releaseDate: String? = null,
	val overview: String? = null,
	@JsonProperty("poster_path") val posterPath: String? = null,
) {

	constructor(productionDocument: ProductionDocument) : this(
		slug = productionDocument.slug!!,
		tmdbId = productionDocument.tmdbId,
		imdbId = productionDocument.imdbId,
		title = productionDocument.title,
		releaseDate = productionDocument.releaseDate,
		overview = productionDocument.overview,
		posterPath = productionDocument.posterPath,
	)
}
