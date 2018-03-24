/*
 * Copyright 2018 Florian Steitz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.fsteitz.extractor.token.matcher

import java.net.URLDecoder
import java.nio.charset.Charset
import java.util.Optional

private const val HTTP_PATTERN = """http[s]*:\/\/"""

/**
 * @author Florian Steitz (florian@fsteitz.com)
 */
abstract class HttpTokenMatcher(private val charset: Charset) : UrlTokenMatcher {

  override val patterns by lazy { pathPatterns.map { HTTP_PATTERN + domainPattern + it } }

  protected abstract val domainPattern: String
  protected abstract val pathPatterns: Collection<String>

  /**
   *
   */
  override fun findTokens(url: String): Optional<Collection<String?>> {
    val matchGroups = ArrayList<MatchGroup?>()

    patterns.forEach {
      val groups = it.toRegex().matchEntire(url)?.groups

      if (groups != null) {
        matchGroups.addAll(groups.toList())
      }
    }

    return if (matchGroups.size > 0) Optional.of(matchGroups.mapIndexed(::mapToken)) else Optional.empty()
  }

  /**
   *
   */
  private fun mapToken(index: Int, matchGroup: MatchGroup?): String? {
    val value = matchGroup?.value
    return if (index > 0) URLDecoder.decode(value, charset.name()) else value
  }
}