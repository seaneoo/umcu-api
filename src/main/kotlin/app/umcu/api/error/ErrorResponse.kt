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

package app.umcu.api.error

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.Hidden
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
@Hidden
data class ErrorResponse(
	val status: Int,
	val error: String,
	val path: String? = null,
	val timestamp: Instant? = Instant.now(),
	val message: String? = null,
)
