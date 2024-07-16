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

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalExceptionHandler(private val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)) {

	/**
	 * Catch all exceptions that are not caught by another handler.
	 */
	@ExceptionHandler(Exception::class)
	fun handleGenericException(req: HttpServletRequest, e: Exception): ResponseEntity<ExceptionResponse> {
		logger.warn(e.stackTraceToString())
		val httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
		val response = ExceptionResponse(
			statusCode = httpStatus.value(),
			statusReason = httpStatus.reasonPhrase,
			path = req.requestURI,
			message = e.message
		)
		return ResponseEntity.status(httpStatus).body(response)
	}

	/**
	 * Catch all [NoResourceFoundException] errors.
	 */
	@ExceptionHandler(NoResourceFoundException::class)
	fun handleNoResourceFoundException(
		req: HttpServletRequest,
		e: NoResourceFoundException,
	): ResponseEntity<ExceptionResponse> {
		val httpStatus = HttpStatus.NOT_FOUND
		val response = ExceptionResponse(
			statusCode = httpStatus.value(), statusReason = httpStatus.reasonPhrase, path = req.requestURI
		)
		return ResponseEntity.status(httpStatus).body(response)
	}

	/**
	 * Catch all [ResponseStatusException] errors.
	 */
	@ExceptionHandler(ResponseStatusException::class)
	fun handleResponseStatusException(
		req: HttpServletRequest,
		e: ResponseStatusException,
	): ResponseEntity<ExceptionResponse> {
		val httpStatus = HttpStatus.valueOf(e.statusCode.value())
		val response = ExceptionResponse(
			statusCode = httpStatus.value(),
			statusReason = httpStatus.reasonPhrase,
			path = req.requestURI,
			message = e.message
		)
		return ResponseEntity.status(httpStatus).body(response)
	}
}
