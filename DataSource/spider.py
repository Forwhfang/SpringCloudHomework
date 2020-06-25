import requests
from lxml import etree
from string import Template
import time
import random
import json
import re

def get_page(url):
    headers = {
        'USER-AGENT':'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36'
    }
    response = requests.get(url = url, headers = headers)
    html = response.text
    return html

def parse_page(html):
    html_elem = etree.HTML(html)
    imgUrl = html_elem.xpath('//tr[@class="item"]/td[1]/a/img/@src')
    name = html_elem.xpath('//tr[@class="item"]/td[2]/div[@class="pl2"]/a/@title')
    info = html_elem.xpath('//tr[@class="item"]/td[2]/p[@class="pl"]/text()')
    star = html_elem.xpath('//div[contains(@class, "star")]/span[@class="rating_nums"]/text()')
    comments = html_elem.xpath('//div[contains(@class, "star")]/span[@class="pl"]/text()')
    quotes = html_elem.xpath('//span[@class="inq"]/text()')

    author, translator, price, pubdate, press = [], [], [], [], []
    for item in info:
        splits = item.split('/')
        splits = [item.strip() for item in splits]
        # 特判
        if item == '[英] 阿·柯南道尔 / 丁钟华 等 / 群众出版社 / 1981-8 / 53.00元/68.00元':
            author.append(splits[0])
            translator.append(splits[1])
            press.append(splits[2])
            pubdate.append(splits[3])
            price.append(splits[4])
        elif item == 'S.A.阿列克谢耶维奇 / 方祖芳 / 花城出版社/铁葫芦图书 / 2014-6-15 / 34.80元':
            author.append(splits[0])
            translator.append(splits[1])
            press.append(splits[2])
            pubdate.append(splits[4])
            price.append(splits[5])
        else:
            author.append(splits[0])
            translator.append('' if len(splits) == 4 else splits[1])
            price.append(splits[-1])        
            pubdate.append(splits[-2])
            press.append(splits[-3])
    
    pattern = r'[+-]?([0-9]*\.?[0-9]+|[0-9]+\.?[0-9]*)([eE][+-]?[0-9]+)?'
    star = [float(re.search(pattern, item).group()) for item in star]
    comments = [int(re.search(pattern, item.lstrip('(').rstrip(')').strip()).group()) for item in comments]
    price = [float(re.search(pattern, item).group()) for item in price]

    data = list(zip(imgUrl, name, star, comments, quotes, author, translator, price, pubdate, press))
    return data

def save2file(all_data):
    meta = ('imgUrl', 'name', 'star', 'comments', 'quotes', 'author', 'translator', 'price', 'pubdate', 'press')
    wrapper = [dict(zip(meta, data)) for data in all_data]
    fd = open('douban.json', 'w', encoding = 'utf-8')
    json.dump(wrapper, fd, ensure_ascii = False)
    fd.close()

def crawl():
    init_url = Template('https://book.douban.com/top250?start=$page')
    all_data = []
    for page in range(0, 250, 25):
        curr_url = init_url.substitute(page = str(page))
        html = get_page(curr_url)
        data = parse_page(html)
        all_data.extend(data)
        time.sleep(random.random())
    save2file(all_data)

if __name__ == '__main__':
    crawl()