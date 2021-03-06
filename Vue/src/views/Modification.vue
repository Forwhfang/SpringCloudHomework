<template>
    <div class="bg">
        <div class="form">
            <div class="form-head">{{title}}</div>
            <img :src="require('../assets/modification/cancel.png')" @click="cancel" class="cancel">
            <div class="flex-row">
                <img id="poster" class="poster" :src="imgUrl" @click="chooseFile">
                <input ref="filesInput" @change="uploadFile" type="file" accept="image/png, image/jpeg" style="display: none;" />
                <div class="text">
                    <div class="form-item">
                        <label class="item-label" for="name">书名<span class="mandatory">*</span></label>
                        <input class="item-input" id="name" v-model="name" />
                    </div>
                    <div class="form-item">
                        <label class="item-label" for="author">作者<span class="mandatory">*</span></label>
                        <input class="item-input" id="author" v-model="author" />
                    </div>
                </div>
            </div>
            <div>
                <div class="form-item">
                    <label class="item-label" for="quotes">简介<span class="mandatory">*</span></label>
                    <input class="item-input" id="quotes" v-model="quotes" />
                </div>
                <div class="form-item">
                    <label class="item-label" for="star">评分<span class="mandatory">*</span></label>
                    <input class="item-input" id="star" v-model="star" />
                </div>
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
                    <datepicker class="item-input-datepicker" id="pubdate" :date="pubdate" :option="dateOption"></datepicker>
                </div>
            </div>
            <div class="submit-field">
                <button class="submit" @click="submit">提交</button>
            </div>
        </div>
        <my-api ref="api"></my-api>
    </div>
</template>

<script>
import myApi from '../components/my-api.vue';
import datepicker from "vue-datepicker/vue-datepicker-es6.vue";
export default {
    name: "Modification",
    components: {
        myApi,
        datepicker
    },
    data() {
        return {
            title: '书籍',
            id: '',
            imgUrl: require('../assets/modification/defaultImage.png'),
            name: '',
            author: '',
            translator: '',
            press: '',
            pubdate: { time: '' },
            quotes: '',
            star: '',
            dateOption: {
                type: 'day',
                week: ['一', '二', '三', '四', '五', '六', '日'],
                month: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
                format: 'YYYY-MM-DD',
                inputStyle: {
                    width: '100%',
                    height: '18px',
                    padding: '5px',
                },
                color: {
                    header: 'rgb(93, 70, 49)',
                    headerText: 'rgb(255, 255, 255)',
                },
                buttons: {
                    ok: '确定',
                    cancel: '取消',
                }
            }
        }
    },
    methods: {
        chooseFile: function() {
            this.$refs.filesInput.click()
        },
        uploadFile: function(e) {
            let file = e.target.files[0]
            let reader = new FileReader()
            reader.onloadend = () => { this.imgUrl = reader.result }
            reader.readAsDataURL(file)
        },
        cancel: function() {
            this.$router.go(-1)
        },
        helper: function(elemId, hint) {
            let elem = document.getElementById(elemId)
			if (elem) {
                elem.value = ''
                elem.placeholder = hint || ''
				elem.classList.add('shake')
				setTimeout(()=>{ elem.classList.remove('shake') }, 800)
			}
        },
        validate: function() {
            if (this.imgUrl.indexOf('defaultImage') !== -1) { // TODO
                this.helper('poster')
                return false
            }
            if (!this.name) {
                this.helper('name', '书名不能为空')
                return false
            }
            if (!this.author) {
                this.helper('author', '作者不能为空')
                return false
            }
            if (!this.quotes) {
                this.helper('quotes', '简介不能为空')
                return false
            }
            if (!this.star) {
                this.helper('star', '评分不能为空')
                return false
            }
            if (!/^([0-9]*\.?[0-9]+|[0-9]+\.?[0-9]*)$/.test(this.star)
                || parseFloat(this.star) <= 0
                || parseFloat(this.star) >= 10
            ) {
                this.helper('star', '评分只能在 0 ~ 10 之间')
                return false
            }
            return true
        },
        submit: async function() {
            let isLegal = this.validate()
            if (isLegal) {
                let formData = {
                    imgUrl: this.imgUrl,
                    name: this.name,
                    author: this.author,
                    translator: this.translator,
                    press: this.press,
                    pubdate: this.pubdate.time,
                    quotes: this.quotes,
                    star: this.star,
                }
                if (this.title === '书籍') {
                    alert('页面错误')
                    this.$router.go(-1)
                }
                else if (this.title === '增加书籍') {
                    let result = await this.$refs.api.createBook(formData)
                    if (result && result.data.code ===  0) {
                        alert('增加成功')
                        this.$router.push('/books')
                        this.$nextTick(() => {
                            this.$root.Bus.$emit('createBookSuccess', result.data.book)
                        })
                    } else  {
                        alert('增加失败')
                    }
                }
                else if (this.title === '修改书籍') {
                    formData.id = this.id
                    let result = await this.$refs.api.updateBook(formData)
                    if (result && result.data.code ===  0) {
                        alert('修改成功')
                        this.$router.push('/books')
                        this.$nextTick(() => {
                            this.$root.Bus.$emit('editBookSuccess', result.data.book)
                        })
                    } else  {
                        alert('修改失败')
                    }
                }
            }
        }
    },
    created: function() {
        this.$root.Bus.$on('createBook', () => {
            this.title = '增加书籍'
        })
        this.$root.Bus.$on('editBook', (selectedBook) => {
            this.title = '修改书籍'
            this.id = selectedBook.id
            this.imgUrl = selectedBook.imgUrl
            this.name = selectedBook.name
            this.author = selectedBook.author
            this.translator = selectedBook.translator
            this.press = selectedBook.press
            this.pubdate.time = selectedBook.pubdate
            this.star = selectedBook.star
            this.quotes = selectedBook.quotes
        })
    },
    beforeDestroy: function() {
        this.$root.Bus.$off('createBook')
        this.$root.Bus.$off('editBook')
    },
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
.cancel {
    width: 20px;
    height: 20px;
    position: absolute;
    top: 30px;
    left: 20px;
}
.cancel:hover {
    cursor: pointer;
}
.form {
    margin: 15px auto;
    padding: 20px;
    position: relative;

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
    margin-bottom: 12px;
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
.item-input-datepicker {
    width: 100%;
    height: 18px;
}
.mandatory {
    color: red;
}
.flex-row {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: flex-start;
}
.poster {
    width: 20px;
    height: 142px;
    margin-right: 20px;
    margin-bottom: 20px;
    flex-grow: 1;
    box-shadow: 3px 3px 6px lightgray;
}
.poster:hover {
    cursor: pointer;
    box-shadow: 3px 3px 10px lightgray;
}
.text {
    flex-grow: 3;
}
.submit-field {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    margin-top: 20px;
}
.submit {
    width: 80px;
    height: 40px;
    border: 0.2px solid lightgray;
    background-color: #f6f6f1;

    font-size: large;
    font-weight: bolder;
    letter-spacing: 5px;
    color: rgb(93, 70, 49);
}
.submit:hover {
    cursor: pointer;
    background-color: rgba(246, 246, 241, 0.6);
}
.shake {
    animation: shake 800ms ease-in-out;
}
@keyframes shake {
    10%, 90% { transform: translate3d(0, -1px, 0); }
    20%, 80% { transform: translate3d(0, +2px, 0); }
    30%, 70% { transform: translate3d(0, -4px, 0); }
    40%, 60% { transform: translate3d(0, +4px, 0); }
    50% { transform: translate3d(0, -4px, 0); }
}
</style>