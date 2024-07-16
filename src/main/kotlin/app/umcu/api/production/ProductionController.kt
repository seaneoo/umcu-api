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
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/productions")
class ProductionController(private val productionService: ProductionService) {

	@GetMapping
	fun findAll(): ResponseEntity<List<Production>> {
		return ResponseEntity.ok(productionService.findAll())
	}

	@GetMapping("/{slug}")
	fun findBySlug(@PathVariable slug: String): ResponseEntity<Production> {
		val production = productionService.findBySlug(slug) ?: throw ProductionNotFoundException()
		return ResponseEntity.ok(production)
	}
}
