package util

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result

fun get(url : String) : Response {
    Fuel.get(url).response().apply { return second }
}

fun newGet(url : String) : Response {
    Fuel.post(url).response().let { triple ->  return triple.second }
}

fun newNewGet(url : String) : Triple<Request, Response, Result<ByteArray, FuelError>> {
    return Fuel.get(url).response()
}

fun post(url : String) : Response {
    Fuel.post(url).response().let { triple ->  return triple.second }
}

fun put(url : String) : Response {
    Fuel.put(url).response().let { triple ->  return triple.second }
}

fun patch(url : String) : Response  {
    Fuel.patch(url).response().let { triple ->  return triple.second }
}

fun delete(url : String) : Response  {
    Fuel.delete(url).response().let { triple ->  return triple.second }
}


