package io.karn.khttp.extensions

import io.karn.khttp.DEFAULT_TIMEOUT
import io.karn.khttp.responses.Response
import io.karn.khttp.structures.authorization.Authorization
import io.karn.khttp.structures.files.FileLike
import io.reactivex.Single
import io.reactivex.SingleEmitter

@JvmOverloads
fun delete(url: String, headers: Map<String, String?> = mapOf(), params: Map<String, String> = mapOf(), data: Any? = null, json: Any? = null, auth: Authorization? = null, cookies: Map<String, String>? = null, timeout: Double = DEFAULT_TIMEOUT, allowRedirects: Boolean? = null, stream: Boolean = false, files: List<FileLike> = listOf()): Single<Response> {
    return Single.create<Response> { emitter: SingleEmitter<Response> ->
        val response: Response = io.karn.khttp.delete(url, headers, params, data, json, auth, cookies, timeout, allowRedirects, stream, files)
        emitter.onSuccess(response)
    }
}

@JvmOverloads
fun get(url: String, headers: Map<String, String?> = mapOf(), params: Map<String, String> = mapOf(), data: Any? = null, json: Any? = null, auth: Authorization? = null, cookies: Map<String, String>? = null, timeout: Double = DEFAULT_TIMEOUT, allowRedirects: Boolean? = null, stream: Boolean = false, files: List<FileLike> = listOf()): Single<Response> {
    return Single.create<Response> { emitter: SingleEmitter<Response> ->
        val response: Response = io.karn.khttp.get(url, headers, params, data, json, auth, cookies, timeout, allowRedirects, stream, files)
        emitter.onSuccess(response)
    }
}

@JvmOverloads
fun head(url: String, headers: Map<String, String?> = mapOf(), params: Map<String, String> = mapOf(), data: Any? = null, json: Any? = null, auth: Authorization? = null, cookies: Map<String, String>? = null, timeout: Double = DEFAULT_TIMEOUT, allowRedirects: Boolean? = null, stream: Boolean = false, files: List<FileLike> = listOf()): Single<Response> {
    return Single.create<Response> { emitter: SingleEmitter<Response> ->
        val response: Response = io.karn.khttp.head(url, headers, params, data, json, auth, cookies, timeout, allowRedirects, stream, files)
        emitter.onSuccess(response)
    }
}

@JvmOverloads
fun options(url: String, headers: Map<String, String?> = mapOf(), params: Map<String, String> = mapOf(), data: Any? = null, json: Any? = null, auth: Authorization? = null, cookies: Map<String, String>? = null, timeout: Double = DEFAULT_TIMEOUT, allowRedirects: Boolean? = null, stream: Boolean = false, files: List<FileLike> = listOf()): Single<Response> {
    return Single.create<Response> { emitter: SingleEmitter<Response> ->
        val response: Response = io.karn.khttp.options(url, headers, params, data, json, auth, cookies, timeout, allowRedirects, stream, files)
        emitter.onSuccess(response)
    }
}

@JvmOverloads
fun patch(url: String, headers: Map<String, String?> = mapOf(), params: Map<String, String> = mapOf(), data: Any? = null, json: Any? = null, auth: Authorization? = null, cookies: Map<String, String>? = null, timeout: Double = DEFAULT_TIMEOUT, allowRedirects: Boolean? = null, stream: Boolean = false, files: List<FileLike> = listOf()): Single<Response> {
    return Single.create<Response> { emitter: SingleEmitter<Response> ->
        val response: Response = io.karn.khttp.patch(url, headers, params, data, json, auth, cookies, timeout, allowRedirects, stream, files)
        emitter.onSuccess(response)
    }
}

@JvmOverloads
fun post(url: String, headers: Map<String, String?> = mapOf(), params: Map<String, String> = mapOf(), data: Any? = null, json: Any? = null, auth: Authorization? = null, cookies: Map<String, String>? = null, timeout: Double = DEFAULT_TIMEOUT, allowRedirects: Boolean? = null, stream: Boolean = false, files: List<FileLike> = listOf()): Single<Response> {
    return Single.create<Response> { emitter: SingleEmitter<Response> ->
        val response: Response = io.karn.khttp.post(url, headers, params, data, json, auth, cookies, timeout, allowRedirects, stream, files)
        emitter.onSuccess(response)
    }
}

@JvmOverloads
fun put(url: String, headers: Map<String, String?> = mapOf(), params: Map<String, String> = mapOf(), data: Any? = null, json: Any? = null, auth: Authorization? = null, cookies: Map<String, String>? = null, timeout: Double = DEFAULT_TIMEOUT, allowRedirects: Boolean? = null, stream: Boolean = false, files: List<FileLike> = listOf()): Single<Response> {
    return Single.create<Response> { emitter: SingleEmitter<Response> ->
        val response: Response = io.karn.khttp.put(url, headers, params, data, json, auth, cookies, timeout, allowRedirects, stream, files)
        emitter.onSuccess(response)
    }
}