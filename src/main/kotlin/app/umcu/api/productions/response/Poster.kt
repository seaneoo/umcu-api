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

import com.fasterxml.jackson.annotation.JsonProperty

const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/%s/%s"

@Suppress("unused")
data class Poster(
    @JsonProperty("key") val posterPath: String,
) {

    private val sizes = listOf("w500", "w780", "original")
    val images: Map<String, String> =
        sizes.associateWith { size -> IMAGE_BASE_URL.format(size, posterPath.replace("^/".toRegex(), "")) }
}
