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
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/productions")
@Validated
class ProductionController(private val productionService: ProductionService) {

	@GetMapping
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

	@GetMapping("/{slug}")
	fun findBySlug(@PathVariable slug: String): ResponseEntity<Production> {
		val production = productionService.findBySlug(slug) ?: throw ProductionNotFoundException()
		return ResponseEntity.ok(production)
	}
}
