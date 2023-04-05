package com.app.thingscrossing.feature_advertisement.data.remote

import com.app.thingscrossing.core.Constants.ADVERTISEMENT_API_BASE_URL
import com.app.thingscrossing.feature_advertisement.domain.model.Currency
import com.google.gson.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDateTime



class ApiAdapter {
    companion object {
        fun buildAdvertisementApi(): AdvertisementApi {
            return Retrofit.Builder()
                .baseUrl(ADVERTISEMENT_API_BASE_URL)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                            .registerTypeAdapter(
                                LocalDateTime::class.java,
                                LocalDateTimeDeserializer()
                            )
                            .registerTypeAdapter(
                                Currency::class.java,
                                CurrencyDeserializer()
                            )
                            .registerTypeAdapter(
                                Currency::class.java,
                                CurrencySerializer()
                            )
                            .create()
                    )
                )
                .build()
                .create(AdvertisementApi::class.java)
        }

    }

}

class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): LocalDateTime {
        return LocalDateTime.parse(json?.asString?.dropLast(1))
    }
}

class CurrencySerializer: JsonSerializer<Currency> {
    override fun serialize(
        src: Currency?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?,
    ): JsonElement {
        val jsonCurrency = JsonObject()
        jsonCurrency.addProperty("currency_code", src?.code)
        return jsonCurrency
    }
}

class CurrencyDeserializer : JsonDeserializer<Currency> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Currency {
        return Currency.fromCode(json?.asString!!)
    }
}