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

package app.umcu.api

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
	info = Info(
		title = OpenApi.INFO_TITLE,
		description = OpenApi.INFO_DESCRIPTION,
		version = OpenApi.INFO_VERSION,
		contact = Contact(url = OpenApi.CONTACT_URL, email = OpenApi.CONTACT_EMAIL),
		license = License(name = OpenApi.LICENSE_NAME, url = OpenApi.LICENSE_URL)
	), servers = [Server(url = OpenApi.SERVER_URL, description = OpenApi.SERVER_DESCRIPTION)]
)
class OpenApiConfiguration

private object OpenApi {

	const val INFO_TITLE = "UpcomingMCU API"
	const val INFO_DESCRIPTION = "The ultimate source for staying up-to-date with the Marvel Cinematic Universe."
	const val INFO_VERSION = "0.1.0"

	const val CONTACT_URL = "https://github.com/upcomingmcu/"
	const val CONTACT_EMAIL = "help@umcu.app"

	const val LICENSE_NAME = "GNU General Public License v3.0"
	const val LICENSE_URL = "https://raw.githubusercontent.com/upcomingmcu/api/main/LICENSE"

	const val SERVER_URL = "https://api.umcu.app/"
	const val SERVER_DESCRIPTION = "Production server"
}
