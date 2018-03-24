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

package com.github.fsteitz.extractor.token.main

import com.github.fsteitz.extractor.token.matcher.LeoOrgTokenMatcher
import com.github.fsteitz.extractor.token.matcher.UrlTokenMatcher
import com.github.fsteitz.extractor.token.reader.BookmarkReader
import java.io.FileNotFoundException
import java.net.URL
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

/**
 * @author Florian Steitz (florian@fsteitz.com)
 */

const val BOOKMARK_JSON = "bookmark.json"

val charset = StandardCharsets.UTF_8 as Charset
val matchers = listOf(
        LeoOrgTokenMatcher(charset)
)

/**
 *
 */
fun main(args: Array<String>) {
  val urls = BookmarkReader.readFromFile(getClasspathResource(BOOKMARK_JSON).file, charset)
  matchers.forEach { extractTokens(urls, it) }
}

/**
 *
 */
fun getClasspathResource(fileName: String): URL {
  return Thread.currentThread().contextClassLoader?.getResource(fileName)
          ?: throw FileNotFoundException("File '$fileName' in classpath not found")
}

/**
 *
 */
private fun extractTokens(urls: Collection<String>, matcher: UrlTokenMatcher) {
  val matcherName = matcher.javaClass.simpleName

  println("Patterns checked by $matcherName:")
  matcher.patterns.forEach { println(" - $it") }

  println("\nTokens extracted by $matcherName:")
  urls.forEach { println(matcher.findTokens(it).orElse(listOf("?"))) }
}