package com.example.kanjidaily.data.seed

import com.example.kanjidaily.data.entity.KanjiEntity
import com.example.kanjidaily.data.entity.VocabularyEntity

object SeedData {
    val kanji = listOf(
        KanjiEntity("日", "sun/day", "ニチ・ジツ", "ひ・か", 4, "N5", "日本 - Japan; 日曜日 - Sunday"),
        KanjiEntity("月", "moon/month", "ゲツ・ガツ", "つき", 4, "N5", "月曜日 - Monday; 一月 - January"),
        KanjiEntity("火", "fire", "カ", "ひ", 4, "N5", "火曜日 - Tuesday; 火山 - volcano"),
        KanjiEntity("水", "water", "スイ", "みず", 4, "N5", "水曜日 - Wednesday; 水道 - water supply"),
        KanjiEntity("木", "tree", "モク・ボク", "き", 4, "N5", "木曜日 - Thursday; 木村 - Kimura"),
        KanjiEntity("金", "gold/money", "キン", "かね", 8, "N5", "金曜日 - Friday; お金 - money"),
        KanjiEntity("土", "earth", "ド・ト", "つち", 3, "N5", "土曜日 - Saturday; 土地 - land"),
        KanjiEntity("人", "person", "ジン・ニン", "ひと", 2, "N5", "日本人 - Japanese person; 人口 - population"),
        KanjiEntity("大", "big", "ダイ・タイ", "おおきい", 3, "N5", "大学 - university; 大きい - big"),
        KanjiEntity("小", "small", "ショウ", "ちいさい", 3, "N5", "小さい - small; 小学校 - elementary school"),
        KanjiEntity("山", "mountain", "サン", "やま", 3, "N5", "山田 - Yamada; 火山 - volcano"),
        KanjiEntity("川", "river", "セン", "かわ", 3, "N5", "川口 - Kawaguchi; 小川 - stream"),
        KanjiEntity("田", "rice field", "デン", "た", 5, "N5", "田中 - Tanaka; 山田 - Yamada"),
        KanjiEntity("学", "study", "ガク", "まなぶ", 8, "N5", "学生 - student; 学校 - school"),
        KanjiEntity("生", "life/birth", "セイ・ショウ", "いきる・うまれる", 5, "N5", "先生 - teacher; 学生 - student"),
        KanjiEntity("先", "previous/ahead", "セン", "さき", 6, "N5", "先生 - teacher; 先月 - last month"),
        KanjiEntity("私", "I/private", "シ", "わたし", 7, "N5", "私立 - private; 私 - I"),
        KanjiEntity("時", "time", "ジ", "とき", 10, "N5", "時間 - time; 時計 - clock"),
        KanjiEntity("行", "go", "コウ・ギョウ", "いく", 6, "N5", "行く - to go; 銀行 - bank"),
        KanjiEntity("食", "eat", "ショク", "たべる", 9, "N5", "食べる - to eat; 食堂 - cafeteria"),
        KanjiEntity("電", "electricity", "デン", "", 13, "N4", "電車 - train; 電話 - phone"),
        KanjiEntity("車", "car", "シャ", "くるま", 7, "N4", "電車 - train; 自動車 - car")
    )

    val vocabulary = listOf(
        VocabularyEntity(word = "日本", reading = "にほん", romaji = "nihon", meaning = "Japan", jlptLevel = "N5", exampleSentence = "日本へ行きます。", exampleTranslation = "I go to Japan."),
        VocabularyEntity(word = "学生", reading = "がくせい", romaji = "gakusei", meaning = "student", jlptLevel = "N5", exampleSentence = "私は学生です。", exampleTranslation = "I am a student."),
        VocabularyEntity(word = "先生", reading = "せんせい", romaji = "sensei", meaning = "teacher", jlptLevel = "N5", exampleSentence = "先生はやさしいです。", exampleTranslation = "The teacher is kind."),
        VocabularyEntity(word = "水", reading = "みず", romaji = "mizu", meaning = "water", jlptLevel = "N5", exampleSentence = "水を飲みます。", exampleTranslation = "I drink water."),
        VocabularyEntity(word = "火曜日", reading = "かようび", romaji = "kayoubi", meaning = "Tuesday", jlptLevel = "N5", exampleSentence = "火曜日に会います。", exampleTranslation = "We meet on Tuesday."),
        VocabularyEntity(word = "月曜日", reading = "げつようび", romaji = "getsuyoubi", meaning = "Monday", jlptLevel = "N5", exampleSentence = "月曜日に学校へ行きます。", exampleTranslation = "I go to school on Monday."),
        VocabularyEntity(word = "山", reading = "やま", romaji = "yama", meaning = "mountain", jlptLevel = "N5", exampleSentence = "山が見えます。", exampleTranslation = "I can see a mountain."),
        VocabularyEntity(word = "川", reading = "かわ", romaji = "kawa", meaning = "river", jlptLevel = "N5", exampleSentence = "川で泳ぎます。", exampleTranslation = "I swim in the river."),
        VocabularyEntity(word = "人", reading = "ひと", romaji = "hito", meaning = "person", jlptLevel = "N5", exampleSentence = "あの人は友達です。", exampleTranslation = "That person is my friend."),
        VocabularyEntity(word = "大きい", reading = "おおきい", romaji = "ookii", meaning = "big", jlptLevel = "N5", exampleSentence = "大きい家です。", exampleTranslation = "It is a big house."),
        VocabularyEntity(word = "小さい", reading = "ちいさい", romaji = "chiisai", meaning = "small", jlptLevel = "N5", exampleSentence = "小さい本です。", exampleTranslation = "It is a small book."),
        VocabularyEntity(word = "食べる", reading = "たべる", romaji = "taberu", meaning = "to eat", jlptLevel = "N5", exampleSentence = "ご飯を食べます。", exampleTranslation = "I eat rice."),
        VocabularyEntity(word = "行く", reading = "いく", romaji = "iku", meaning = "to go", jlptLevel = "N5", exampleSentence = "駅へ行きます。", exampleTranslation = "I go to the station."),
        VocabularyEntity(word = "見る", reading = "みる", romaji = "miru", meaning = "to see", jlptLevel = "N5", exampleSentence = "映画を見ます。", exampleTranslation = "I watch a movie."),
        VocabularyEntity(word = "来る", reading = "くる", romaji = "kuru", meaning = "to come", jlptLevel = "N5", exampleSentence = "友達が来ます。", exampleTranslation = "My friend comes."),
        VocabularyEntity(word = "今日", reading = "きょう", romaji = "kyou", meaning = "today", jlptLevel = "N5", exampleSentence = "今日は暑いです。", exampleTranslation = "Today is hot."),
        VocabularyEntity(word = "明日", reading = "あした", romaji = "ashita", meaning = "tomorrow", jlptLevel = "N5", exampleSentence = "明日勉強します。", exampleTranslation = "I will study tomorrow."),
        VocabularyEntity(word = "時間", reading = "じかん", romaji = "jikan", meaning = "time", jlptLevel = "N5", exampleSentence = "時間があります。", exampleTranslation = "I have time."),
        VocabularyEntity(word = "電車", reading = "でんしゃ", romaji = "densha", meaning = "train", jlptLevel = "N5", exampleSentence = "電車に乗ります。", exampleTranslation = "I ride the train."),
        VocabularyEntity(word = "友達", reading = "ともだち", romaji = "tomodachi", meaning = "friend", jlptLevel = "N5", exampleSentence = "友達と話します。", exampleTranslation = "I talk with a friend."),
        VocabularyEntity(word = "電話", reading = "でんわ", romaji = "denwa", meaning = "phone", jlptLevel = "N4", exampleSentence = "母に電話します。", exampleTranslation = "I call my mother."),
        VocabularyEntity(word = "会社", reading = "かいしゃ", romaji = "kaisha", meaning = "company", jlptLevel = "N4", exampleSentence = "会社へ行きます。", exampleTranslation = "I go to the company.")
    )
}
