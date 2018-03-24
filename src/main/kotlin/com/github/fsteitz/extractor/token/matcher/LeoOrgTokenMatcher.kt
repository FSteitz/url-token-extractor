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

import java.nio.charset.Charset

private const val DOMAIN_PATTERN = """(?:pda|dict)\.leo\.org\/"""
private const val QUERY_PATTERN = """[a-zA-Z=0-9]*(?:&[a-zA-Z=0-8]*)*(?:search=([^&]*)).*?"""

/**
 * @author Florian Steitz (florian@fsteitz.com)
 */
class LeoOrgTokenMatcher(charset: Charset) : HttpTokenMatcher(charset) {

  override val domainPattern = DOMAIN_PATTERN
  override val pathPatterns by lazy {
    listOf(
            """ende\?$QUERY_PATTERN""",
            """dictQuery\/m-vocab\/ende\/de\.html\?$QUERY_PATTERN""",
            """#\/$QUERY_PATTERN""",
            """englisch-deutsch\/([^&]*)"""
    )
  }
}