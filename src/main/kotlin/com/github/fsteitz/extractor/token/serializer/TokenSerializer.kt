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

package com.github.fsteitz.extractor.token.serializer

import com.github.fsteitz.extractor.token.TokenData
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

/**
 * @author Florian Steitz (florian@fsteitz.com)
 */
@Serializable
private data class TokenDataContainer(val tokenData: Collection<TokenData>)

/**
 * @author Florian Steitz (florian@fsteitz.com)
 */
class TokenSerializer(private val tokens: Collection<TokenData>) {

  /**
   *
   */
  fun writeToDisk(pathName: String, charset: Charset) {
    val fileContent = JSON.stringify(TokenDataContainer(tokens))

    println("Writing extracted token data to '$pathName'")
    Files.write(Paths.get(pathName), fileContent.toByteArray(charset))
  }
}