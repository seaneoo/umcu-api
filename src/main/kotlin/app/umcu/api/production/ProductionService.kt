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

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class ProductionService(private val productionRepository: ProductionRepository) {

	fun findAll(): List<Production> {
		return productionRepository.findAll().sortedWith(compareBy(nullsLast()) {
			it.releaseDate?.let { dateStr -> LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE) }
		})
	}

	fun findAllPaged(page: Int = 0, size: Int = 10): Paged {
		require(page > 0) { "Page must be greater than 0" }
		require(size > 0) { "Size must be greater than 0" }

		val productions = findAll()
		val totalPages = if (productions.isNotEmpty()) ((productions.size + size - 1) / size) else 0

		val fromIndex = (page - 1) * size
		val toIndex = minOf(fromIndex + size, productions.size)

		if (fromIndex >= productions.size) return Paged(
			page = page, size = size, totalPages = totalPages, totalResults = productions.size, results = emptyList()
		)

		return Paged(
			page = page,
			size = size,
			totalPages = totalPages,
			totalResults = productions.size,
			results = productions.subList(fromIndex, toIndex)
		)
	}

	fun findBySlug(slug: String): Production? {
		return productionRepository.findBySlug(slug)
	}
}
