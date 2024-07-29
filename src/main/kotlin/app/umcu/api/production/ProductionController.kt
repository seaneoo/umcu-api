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

package app.umcu.api.production

import app.umcu.api.error.ProductionNotFoundException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/productions")
@Validated
@Tag(name = "Productions")
class ProductionController(private val productionService: ProductionService) {

	@GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
	@Operation(summary = "All Productions", description = "Get all productions.")
	fun findAll(
		@RequestParam(required = false) @Min(1, message = "Page must be at least 1") page: Int = 1,
		@RequestParam(required = false) @Min(1, message = "Size must be at least 1") size: Int = 10,
		@RequestParam(required = false) @Pattern(
			regexp = "(?i)^(upcoming|released|announced)$",
			message = "Status must be 'upcoming', 'released', or 'announced'."
		) status: String? = null,
	): ResponseEntity<Paged> {
		return ResponseEntity.ok(productionService.findAllPaged(page, size, status))
	}

	@GetMapping("/{slug}", produces = [MediaType.APPLICATION_JSON_VALUE])
	@Operation(summary = "Production By Slug", description = "Get a single production by a slug.")
	fun findBySlug(@PathVariable slug: String): ResponseEntity<Production> {
		val production = productionService.findBySlug(slug) ?: throw ProductionNotFoundException()
		return ResponseEntity.ok(production)
	}

	@GetMapping("/next", produces = [MediaType.APPLICATION_JSON_VALUE])
	@Operation(summary = "Next Production", description = "Get the next production to-be-released.")
	fun findNext(): ResponseEntity<Production> {
		val production = productionService.findNext() ?: throw ProductionNotFoundException()
		return ResponseEntity.ok(production)
	}
}
