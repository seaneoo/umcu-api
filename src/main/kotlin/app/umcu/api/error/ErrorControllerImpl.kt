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

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.server.ResponseStatusException


@RestController
@Hidden
class ErrorControllerImpl(private val errorAttributes: DefaultErrorAttributes) : ErrorController {

	@RequestMapping("/error", produces = [MediaType.APPLICATION_JSON_VALUE])
	fun handleError(request: WebRequest): ResponseEntity<ErrorResponse> {
		val attributes = errorAttributes.getErrorAttributes(
			request, ErrorAttributeOptions.of(
				ErrorAttributeOptions.Include.PATH,
				ErrorAttributeOptions.Include.MESSAGE,
				ErrorAttributeOptions.Include.STATUS,
				ErrorAttributeOptions.Include.ERROR,
				ErrorAttributeOptions.Include.EXCEPTION
			)
		)

		var status = attributes["status"] as Int
		var message = attributes["message"] as String

		val exceptionObj = attributes["exception"]
		if (exceptionObj != null) {
			try {
				val aClass = Class.forName(exceptionObj as String)
				if (ResponseStatusException::class.java.isAssignableFrom(aClass)) {
					val responseStatusException =
						aClass.getDeclaredConstructor().newInstance() as ResponseStatusException
					status = responseStatusException.statusCode.value()
					message = responseStatusException.reason.toString()
				}
			} catch (e: Exception) {
				throw RuntimeException(e)
			}
		}

		val httpStatus = HttpStatus.valueOf(status)
		val error = httpStatus.reasonPhrase
		val path = attributes["path"] as String

		return ResponseEntity.ok(
			ErrorResponse(
				status = status, error = error, path = path, message = message
			)
		)
	}
}
