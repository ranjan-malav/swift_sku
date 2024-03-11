package com.ranjan.malav.swiftsku.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ranjan.malav.swiftsku.data.model.PriceBookItem

/**
 * Dummy data, here we are directly sending it through the repository but it can be
 * request from a remote source or from local DB
 */
class PriceBookRepository {
    fun getPriceBook(): List<PriceBookItem> {
        val type = object : TypeToken<List<PriceBookItem>>() {}.type
        return Gson().fromJson(dummyData, type)
    }
}

const val dummyData = "[\n" +
        "        {\n" +
        "            \"pluId\": \"1\",\n" +
        "            \"itemName\": \"Milky Bar\",\n" +
        "            \"taxRates\": [\n" +
        "                5.5\n" +
        "            ],\n" +
        "            \"price\": 1.5\n" +
        "        },\n" +
        "        {\n" +
        "            \"pluId\": \"2\",\n" +
        "            \"itemName\": \"Wheat Bread\",\n" +
        "            \"taxRates\": [],\n" +
        "            \"price\": 5.75\n" +
        "        },\n" +
        "        {\n" +
        "            \"pluId\": \"3\",\n" +
        "            \"itemName\": \"Milk\",\n" +
        "            \"taxRates\": [],\n" +
        "            \"price\": 3\n" +
        "        },\n" +
        "        {\n" +
        "            \"pluId\": \"4\",\n" +
        "            \"itemName\": \"Malboro\",\n" +
        "            \"taxRates\": [\n" +
        "                10,\n" +
        "                8\n" +
        "            ],\n" +
        "            \"price\": 4.25\n" +
        "        },\n" +
        "        {\n" +
        "            \"pluId\": \"5\",\n" +
        "            \"itemName\": \"Walnut\",\n" +
        "            \"taxRates\": [],\n" +
        "            \"price\": 8.5\n" +
        "        },\n" +
        "        {\n" +
        "            \"pluId\": \"6\",\n" +
        "            \"itemName\": \"Hair Oil\",\n" +
        "            \"taxRates\": [\n" +
        "                5.5\n" +
        "            ],\n" +
        "            \"price\": 3.25\n" +
        "        },\n" +
        "        {\n" +
        "            \"pluId\": \"7\",\n" +
        "            \"itemName\": \"Coke\",\n" +
        "            \"taxRates\": [\n" +
        "                15\n" +
        "            ],\n" +
        "            \"price\": 6\n" +
        "        },\n" +
        "        {\n" +
        "            \"pluId\": \"8\",\n" +
        "            \"itemName\": \"Liquor\",\n" +
        "            \"taxRates\": [\n" +
        "                20\n" +
        "            ],\n" +
        "            \"price\": 2.5\n" +
        "        },\n" +
        "        {\n" +
        "            \"pluId\": \"9\",\n" +
        "            \"itemName\": \"Donut\",\n" +
        "            \"taxRates\": [\n" +
        "                6\n" +
        "            ],\n" +
        "            \"price\": 6\n" +
        "        },\n" +
        "        {\n" +
        "            \"pluId\": \"10\",\n" +
        "            \"itemName\": \"Soda\",\n" +
        "            \"taxRates\": [\n" +
        "                12\n" +
        "            ],\n" +
        "            \"price\": 2.5\n" +
        "        }\n" +
        "    ]"