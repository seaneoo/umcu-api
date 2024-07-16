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

package app.umcu.api.ext

import java.text.Normalizer

/**
 * Converts a String into a slug.
 *
 * @author <a href="https://gist.github.com/adrianoluis/641e21dc24a1dbfb09e203d857ae76a3">adrianoluis</a>
 */
fun String.toSlug() = Normalizer.normalize(this, Normalizer.Form.NFD).replace("[^\\p{ASCII}]".toRegex(), "")
	.replace("[^a-zA-Z0-9\\s]+".toRegex(), "").trim().replace("\\s+".toRegex(), "-").lowercase()
