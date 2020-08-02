package cn.kaicity.app.timetable

import java.util.*

//
//object SaveManager {
//    fun saveSticker(stickers: HashMap<Int, Sticker>): String {
//        val obj1 = JsonObject()
//        val arr1 = JsonArray()
//        val orders = getSortedKeySet(stickers)
//        for (i in orders.indices) {
//            val obj2 = JsonObject()
//            val idx = orders[i]
//            obj2.addProperty("idx", orders[i])
//            val arr2 = JsonArray() //5
//            val schedules = stickers[idx]!!.getSchedules()
//            for (schedule in schedules) {
//                val obj3 = JsonObject()
//                obj3.addProperty("classTitle", schedule.classTitle)
//                obj3.addProperty("classPlace", schedule.classPlace)
//                obj3.addProperty("professorName", schedule.getProfessorName())
//                obj3.addProperty("day", schedule.getDay())
//                val obj4 = JsonObject() //startTime
//                obj4.addProperty("hour", schedule.getStartTime().getHour())
//                obj4.addProperty("minute", schedule.getStartTime().getMinute())
//                obj3.add("startTime", obj4)
//                val obj5 = JsonObject() //endtTime
//                obj5.addProperty("hour", schedule.getEndTime().getHour())
//                obj5.addProperty("minute", schedule.getEndTime().getMinute())
//                obj3.add("endTime", obj5)
//                arr2.add(obj3)
//            }
//            obj2.add("schedule", arr2)
//            arr1.add(obj2)
//        }
//        obj1.add("sticker", arr1)
//        return obj1.toString()
//    }
//
//    fun loadSticker(json: String?): HashMap<Int, Sticker> {
//        val stickers = HashMap<Int, Sticker>()
//        val parser = JsonParser()
//        val obj1: JsonObject = parser.parse(json) as JsonObject
//        val arr1: JsonArray = obj1.getAsJsonArray("sticker")
//        for (i in 0 until arr1.size()) {
//            val sticker = Sticker()
//            val obj2: JsonObject = arr1.get(i) as JsonObject
//            val idx: Int = obj2.get("idx").getAsInt()
//            val arr2: JsonArray = obj2.get("schedule") as JsonArray
//            for (k in 0 until arr2.size()) {
//                val schedule = Schedule()
//                val obj3: JsonObject = arr2.get(k) as JsonObject
//                schedule.setClassTitle(obj3.get("classTitle").getAsString())
//                schedule.setClassPlace(obj3.get("classPlace").getAsString())
//                schedule.setProfessorName(obj3.get("professorName").getAsString())
//                schedule.setDay(obj3.get("day").getAsInt())
//                val startTime = Time()
//                val obj4: JsonObject = obj3.get("startTime") as JsonObject
//                startTime.setHour(obj4.get("hour").getAsInt())
//                startTime.setMinute(obj4.get("minute").getAsInt())
//                val endTime = Time()
//                val obj5: JsonObject = obj3.get("endTime") as JsonObject
//                endTime.setHour(obj5.get("hour").getAsInt())
//                endTime.setMinute(obj5.get("minute").getAsInt())
//                schedule.setStartTime(startTime)
//                schedule.setEndTime(endTime)
//                sticker.addSchedule(schedule)
//            }
//            stickers[idx] = sticker
//        }
//        return stickers
//    }
//
//    private fun getSortedKeySet(stickers: HashMap<Int, Sticker>): IntArray {
//        val orders = IntArray(stickers.size)
//        var i = 0
//        for (key in stickers.keys) {
//            orders[i++] = key
//        }
//        Arrays.sort(orders)
//        return orders
//    }
//}
