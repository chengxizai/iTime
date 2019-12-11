package com.example.itime;

    public class DataHolder {
        private int year,month,day,hour,minute;
        private String time,name;

        public int getYear() { return year; }
        public int getMonth() { return month; }
        public int getDay() { return day;}
        public int getHour() { return hour; }
        public int getMinute() { return minute; }
        public String getTime() { return time; }
        public String getName() { return name; }

        public void setYear(int year) { this.year = year; }
        public void setMonth(int month) { this.month = month; }
        public void setDay(int day) { this.day = day; }
        public void setHour(int hour) { this.hour = hour; }
        public void setMinute(int minute) { this.minute = minute; }
        public void setTime(String time) { this.time = time; }
        public void setName(String name) { this.name = name; }

        private static final DataHolder holder = new DataHolder();
        public static DataHolder getInstance() { return holder; }
    }
