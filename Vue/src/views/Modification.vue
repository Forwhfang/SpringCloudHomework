<template>
    <div class="bg">
        <div class="form">
            <div class="form-head">{{title}}</div>

            <div class="mandatory">
            <div class="poster"></div>
            <div class="text">
                <div class="form-item">
                <label class="item-label" for="name">书名</label>
                <input class="item-input" id="name" v-model="name" />
                </div>
                <div class="form-item">
                <label class="item-label" for="author">作者</label>
                <input class="item-input" id="author" v-model="author" />
                </div>
            </div>
            </div>

            <div class="optional">
            <div class="form-item">
                <label class="item-label" for="translator">译者</label>
                <input class="item-input" id="translator" v-model="translator" />
            </div>
            <div class="form-item">
                <label class="item-label" for="press">出版社</label>
                <input class="item-input" id="press" v-model="press" />
            </div>
            <div class="form-item">
                <label class="item-label" for="pubdate">出版日期</label>
                <input class="item-input" id="pubdate" v-model="pubdate" />
            </div>
            <div class="form-item">
                <label class="item-label" for="quote">简介</label>
                <input class="item-input" id="quote" v-model="quote" />
            </div>
            <div class="form-item">
                <label class="item-label" for="star">评分</label>
                <input class="item-input" id="star" v-model="star" />
            </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "Modification",
    props: {
        book: Object,
    },
    data() {
        return {
            title: '书籍',
        }
    },
    watch: {
        book(v) {
            if (v) this.title = "修改"
        }
    }
};
</script>

<style>
.bg {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: #f6f6f1;
}
.form {
    margin: 20px auto;
    padding: 20px;

    border: 1px solid gray;
    box-shadow: 10px 10px 25px 1px lightgray;
    background-color: white;

    width: 600px;
    min-width: 600px;
}
.form-head {
    font-size: xx-large;
    font-weight: bolder;
    letter-spacing: 5px;
    margin-bottom: 20px;
    color: rgb(93, 70, 49);
}
.form-item {
    margin-bottom: 20px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
}
.item-label {
    font-size: large;
    font-weight: bold;
    letter-spacing: 5px;
    margin-bottom: 8px;
    color: rgb(93, 70, 49, 0.8);
}
.item-input {
    width: 100%;
    height: 18px;
    padding: 5px;
}
.mandatory {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: flex-start;
}
.poster {
    width: 20px;
    height: 150px;
    margin-right: 20px;
    margin-bottom: 20px;
    flex-grow: 1;

    border: 1px solid black;
}
.text {
    flex-grow: 2;
}
</style>