/**
 * Copyright 2018 人人开源 http://www.renren.io
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cc.www.common.utils

import org.apache.commons.lang.StringUtils
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

import java.text.SimpleDateFormat
import java.util.Date

/**
 * 日期处理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
object DateUtils {
    /**
     * 时间格式(yyyy-MM-dd)
     */
    val DATE_PATTERN = "yyyy-MM-dd"
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date    日期
     * @param pattern 格式，如：DateUtils.DATE_TIME_PATTERN
     * @return 返回yyyy-MM-dd格式日期
     */
    @JvmOverloads
    fun format(date: Date?, pattern: String = DATE_PATTERN): String? {
        if (date != null) {
            val df = SimpleDateFormat(pattern)
            return df.format(date)
        }
        return null
    }

    /**
     * 字符串转换成日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    fun stringToDate(strDate: String, pattern: String): Date? {
        if (StringUtils.isBlank(strDate)) {
            return null
        }

        val fmt = DateTimeFormat.forPattern(pattern)
        return fmt.parseLocalDateTime(strDate).toDate()
    }

    /**
     * 根据周数，获取开始日期、结束日期
     *
     * @param week 周期  0本周，-1上周，-2上上周，1下周，2下下周
     * @return 返回date[0]开始日期、date[1]结束日期
     */
    fun getWeekStartAndEnd(week: Int): Array<Date> {
        val dateTime = DateTime()
        var date = LocalDate(dateTime.plusWeeks(week))

        date = date.dayOfWeek().withMinimumValue()
        val beginDate = date.toDate()
        val endDate = date.plusDays(6).toDate()
        return arrayOf(beginDate, endDate)
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date    日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    fun addDateSeconds(date: Date, seconds: Int): Date {
        val dateTime = DateTime(date)
        return dateTime.plusSeconds(seconds).toDate()
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date    日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    fun addDateMinutes(date: Date, minutes: Int): Date {
        val dateTime = DateTime(date)
        return dateTime.plusMinutes(minutes).toDate()
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date  日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    fun addDateHours(date: Date, hours: Int): Date {
        val dateTime = DateTime(date)
        return dateTime.plusHours(hours).toDate()
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    fun addDateDays(date: Date, days: Int): Date {
        val dateTime = DateTime(date)
        return dateTime.plusDays(days).toDate()
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param date  日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    fun addDateWeeks(date: Date, weeks: Int): Date {
        val dateTime = DateTime(date)
        return dateTime.plusWeeks(weeks).toDate()
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date   日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    fun addDateMonths(date: Date, months: Int): Date {
        val dateTime = DateTime(date)
        return dateTime.plusMonths(months).toDate()
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date  日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    fun addDateYears(date: Date, years: Int): Date {
        val dateTime = DateTime(date)
        return dateTime.plusYears(years).toDate()
    }
}
/**
 * 日期格式化 日期格式为：yyyy-MM-dd
 *
 * @param date 日期
 * @return 返回yyyy-MM-dd格式日期
 */
