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

package app.umcu.api.productions

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Suppress("unused")
@Document(collection = "productions")
class ProductionDocument(
	@Id val id: String? = null,
	@Indexed(unique = true) var slug: String? = null,
	@Indexed @Field("tmdb_id") val tmdbId: Int,
	@Field("imdb_id") val imdbId: String? = null,
	val title: String,
	@Indexed @Field("release_date") val releaseDate: String? = null,
	@Field("poster_path") val posterPath: String? = null,
	val overview: String? = null,
)
