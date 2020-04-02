package com.myrealtrip.news.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
class Rss {
    @get:Element(name = "channel", required = true)
    @set:Element(name = "channel", required = true)
    var channel: Channel? = null
}

@Root(name = "channel", strict = false)
class Channel {
    @get:ElementList(name = "item", entry = "item", inline = true)
    @set:ElementList(name = "item", entry = "item", inline = true)
    var item: List<Item> = mutableListOf()
}

@Root(name = "item", strict = false)
class Item {
    @set:Element(name = "title")
    @get:Element(name = "title")
    var title: String = ""

    @set:Element(name = "link")
    @get:Element(name = "link")
    var link: String = ""

    @get:Element(name = "description")
    @set:Element(name = "description")
    var description: String = ""
}