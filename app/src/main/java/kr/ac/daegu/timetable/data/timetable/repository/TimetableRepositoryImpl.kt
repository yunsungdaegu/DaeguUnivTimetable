package kr.ac.daegu.timetable.data.timetable.repository

import android.util.Log
import kr.ac.daegu.timetable.BuildConfig
import kr.ac.daegu.timetable.data.timetable.dao.TimetableDao
import kr.ac.daegu.timetable.data.timetable.mapper.mapperToSEL2EntityList
import kr.ac.daegu.timetable.data.timetable.mapper.mapperToSEL5EntityList
import kr.ac.daegu.timetable.data.timetable.repository.datasource.TimetableDataStore
import kr.ac.daegu.timetable.data.utils.connect
import kr.ac.daegu.timetable.domain.timetable.model.SEL2
import kr.ac.daegu.timetable.domain.timetable.model.SEL5
import kr.ac.daegu.timetable.domain.timetable.model.TimetableConfig
import kr.ac.daegu.timetable.domain.timetable.repository.TimetableRepository
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.ByteArrayInputStream
import java.io.InputStream

class TimetableRepositoryImpl(
    private val timetableDao: TimetableDao,
    private val timetableDataStore: TimetableDataStore
) : TimetableRepository {

    override suspend fun getTimetable(year: String, semester: String) {
        val postParams = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Root xmlns=\"http://www.nexacroplatform.com/platform/dataset\"><Parameters><Parameter id=\"YEAR\">${year}</Parameter><Parameter id=\"HAKGI\">${semester}</Parameter></Parameters></Root>"
        val xml = connect(BuildConfig.TIGERSSTD_TIMETABLE, postParams)

        // xml 파싱
        timetableDao.removeAllTimetableSEL2()
        timetableDao.removeAllTimetableSEL5()
        try {
            val parserFactory = XmlPullParserFactory.newInstance()
            val xmlParser = parserFactory.newPullParser()
            xmlParser.setInput((ByteArrayInputStream(xml.toByteArray()) as InputStream).reader())
            var eventType = xmlParser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                var name: String?
                if (eventType == XmlPullParser.START_TAG) {
                    name = xmlParser.name
                    when (name) {
                        "Col" -> {
                            if (xmlParser.getAttributeValue(0) == "LOGIN_NAME_KR") {
                                // 이름 저장
                                val myName = xmlParser.nextText()
                                timetableDataStore.saveTimetableConfig(TimetableConfig(myName, year, semester))
                            }
                        }
                        "Dataset" -> {
                            when(xmlParser.getAttributeValue(0)) {
                                "SEL2" -> {
                                    readSEL2(xmlParser)
                                } // 시간표
                                "SEL5" -> {
                                    readSEL5(xmlParser)
                                } // 가상 시간표
                            }
                        }
                    }
                }
                eventType = xmlParser.next()
            }
        } catch (t: Throwable) {
            Log.d("log", t.toString())
        }
    }

    private fun readSEL2(parser: XmlPullParser) {
        while ((parser.name != "Row") and (parser.eventType != XmlPullParser.END_DOCUMENT))
            parser.next()
        /** 수업이 1주일에 2번이고 연속적이라면 파싱할 수 없는 케이스가 있어 로직 수정 **/
        val array = ArrayList<SEL2>()
        while ((parser.name != "Rows")  and (parser.eventType != XmlPullParser.END_DOCUMENT)) {
            if (parser.eventType == XmlPullParser.START_TAG && parser.name != "Row") {
                when (parser.getAttributeValue(0)) {
                    "SIGAN" -> {
                        val text = parser.nextText()
                        array.add(SEL2(text))
                    }
                    "TM" -> {
                        val t = parser.nextText().split("-")
                        array.last().TM_START_H = t[0].substring(0, 2).toInt()
                        array.last().TM_START_M = t[0].substring(2).toInt()
                        array.last().TM_END_H = t[1].substring(0, 2).toInt()
                        array.last().TM_END_M = t[1].substring(2).toInt()
                    }
                    "YOIL" -> array.last().YOIL = parser.nextText()
                    "NAME_KR" -> array.last().NAME_KR = parser.nextText()
                    "PROF_NM" -> array.last().PROF_NM = parser.nextText()
                    "SUUP_ROOMNAME" -> array.last().SUUP_ROOMNAME = parser.nextText()
                }
            }
            parser.next()
        }
        // 시간표 저장
        val timetable = ArrayList<SEL2>()
        for (i in 0..array.lastIndex) {
            val n = array[i]
            n.GWAMOK = i.toString()
            if (timetable.isEmpty()) {
                timetable.add(n)
                continue
            }
            if ((n.NAME_KR == timetable.last().NAME_KR) and
                (n.YOIL == timetable.last().YOIL)) {
                timetable.last().TM_END_H = n.TM_END_H
                timetable.last().TM_END_M = n.TM_END_M
            } else timetable.add(n)
        }
        timetableDao.insertTimetableSEL2(timetable.toList().mapperToSEL2EntityList())
    }

    private fun readSEL5(parser: XmlPullParser) {
        while ((parser.name != "Row") and (parser.eventType != XmlPullParser.END_DOCUMENT))
            parser.next()
        val array = ArrayList<SEL5>()
        var position = -1
        while ((parser.name != "Rows") and (parser.eventType != XmlPullParser.END_DOCUMENT)) {
            if (parser.eventType == XmlPullParser.START_TAG && parser.name != "Row") {
                when (parser.getAttributeValue(0)) {
                    "GWAMOK" -> {
                        position++
                        array.add(SEL5(GWAMOK = parser.nextText().toInt()))
                    }
                    "NAME_KR" -> array[position].NAME_KR = parser.nextText()
                    "PROF_NM" -> array[position].PROF_NM = parser.nextText()
                }
            }
            parser.next()
        }
        // 가상 시간표 저장
        timetableDao.insertTimetableSEL5(array.toList().mapperToSEL5EntityList())
    }
}