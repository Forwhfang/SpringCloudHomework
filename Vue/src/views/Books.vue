<template>
	<div class="books_root">
		<div class="books_root_head">
			<div class="books_root_head_title">个人书单</div>
			<div class="books_root_head_search">
				<input class="books-search_text" placeholder="书名、作者、出版信息" v-model="searchValue" @keyup.enter="search"/>
				<img class="books-search_icon" src="../assets/books/search.png" @click="search" title="搜索"/>
			</div>
		</div>
		<div class="books_root_body">
			<div v-for="(books_index, index) in booksShowing" :key="books[books_index].id">
				<div class="books_root_body_book">
					<img v-show="false" class="books-book_image" :src="books[books_index].imgUrl" :ref="`book_image_${index}`" @load="justifyImage(index, 100)"/>
					<div class="books-book_content">
						<div class="books-book-head">
							<div class="books-book-title">{{books[books_index].name}}</div>
							<img class="books-book-edit" src="../assets/books/edit.png" @click="bookEdit(books_index)" title="编辑"/>
							<img class="books-book-delete" src="../assets/books/delete.png" @click="bookDelete(books_index)" title="删除"/>
						</div>
						<div class="books-book-info">{{bookInfos[books_index]}}</div>
						<my-rate :total-score="10" :rate="books[books_index].star/10" :size="15"></my-rate>
						<div class="books-book-quote">{{'—— ' + books[books_index].quotes}}</div>
					</div>
				</div>
			</div>
			<div class="books_root_body_create">
				<img class="books-create_icon" src="../assets/books/create.png" @click="bookCreate" title="新建"/>
			</div>
		</div>
		<!-- 方法依赖 -->
		<my-api ref="my_api"></my-api>
	</div>
</template>

<script>
	import myRate from '../components/my-rate.vue';
	import myApi from '../components/my-api.vue';
	export default {
		name: "Books",
		components:{
			myRate,
			myApi
		},
		data() {
			return {
				searchValue: '',
				books: [],
				booksShowing: []
			}
		},
		computed: {
			bookInfos(){
				/**
				 * 构建info
				 * @return: {Array}
				 * */
				let infos = [];
				this.books.forEach(book => {
					let info = '';
					if(book.author) info += `${book.author} / `;
					if(book.translator) info += `${book.translator} / `;
					if(book.pubdate) info += `${book.pubdate} / `;
					if(book.press) info += `${book.press} / `;
					if(info) info = info.slice(0, -3);
					infos.push(info);
				})
				return infos;
			}
		},
		methods:{
			search(){
				/**
				 * 搜索按钮
				 * */
				// 去除前后空格
				this.searchValue = this.searchValue.trim();
				if(this.searchValue.length === 0){
					this.booksShowing = [];
					for(let i=0; i<this.books.length; i++) this.booksShowing.push(i);
					return null;
				}
				this.booksShowing = [];
				this.books.forEach((book, index) =>{
					if(book.name.indexOf(this.searchValue) > -1
						|| book.author.indexOf(this.searchValue) > -1
						|| book.translator.indexOf(this.searchValue) > -1
						|| book.pubdate.indexOf(this.searchValue) > -1
						|| book.press.indexOf(this.searchValue) > -1) {
						this.booksShowing.push(index);
					}
				})
			},
			bookCreate(){
				/**
				 * 书籍新增按钮
				 * */
				this.$router.push('/modification')
				this.$nextTick(() => {
					this.$root.Bus.$emit('createBook')
				})
			},
			bookEdit(_index){
				/**
				 * 书籍修改按钮
				 * @param: {Number} _index books对应的index
				 * */
				this.$router.push('/modification')
				this.$nextTick(() => {
					this.$root.Bus.$emit('editBook', this.books[_index])
				})
			},
			async bookDelete(_index){
				/**
				 * 书籍删除按钮
				 * @param: {Number} _index books对应的index
				 * */
				if(confirm(`您确定要删除《${this.books[_index].name}》吗？`)){
					let deleteRe = await this.$refs.my_api.deleteBookById(this.books[_index].id);
					if(deleteRe.code === 0) {
						this.books.splice(_index, 1);
						// 刷新booksShowing
						this.search();
					}
				}
			},
			// getImgPath(_url){
			// 	/**
			// 	 * 解决图片403问题：使用CDN获取图片
			// 	 * */
			// 	if(_url !== undefined){
			// 		if(_url.substr(0, 8) === 'https://'){
			// 			// 网络图片重构路径
			// 			let subUrl = _url.substring(7);
			// 			return 'https://images.weserv.nl/?url=' + subUrl;
			// 		}
			// 		else {
			// 			// 其他，直接返回（应该只有base64吧）
			// 			return _url;
			// 		}
			// 	}
			// },
			justifyImage(_index, _width){
				/**
				 * 图片按设置宽度进行等比例放缩
				 * @param: {Number} _index v-for列表index
				 * @param: {Number} _width 图片要显示的宽度
				 * */
				let originWidth = this.$refs[`book_image_${_index}`][0].width;
				let originHeight = this.$refs[`book_image_${_index}`][0].height;
				let scale = _width/originWidth;
				this.$refs[`book_image_${_index}`][0].width = _width;
				this.$refs[`book_image_${_index}`][0].height = originHeight * scale;
				this.$refs[`book_image_${_index}`][0].style.display = 'flex';
			}
		},
		created: function() {
			this.$root.Bus.$on('createBookSuccess', (newBook) => {
				console.log('createBookSuccess', newBook)
				// TODO
				// 新增成功，更新列表
			})
			this.$root.Bus.$on('editBookSuccess', (newBook) => {
				console.log('editBookSuccess', newBook)
				// TODO
				// 修改成功，更新列表
			})
		},
		mounted: async function() {
			// 初始化 books、booksShowing
			let getAllBooksRe = await this.$refs.my_api.getAllBooks();
			if(getAllBooksRe.code === 0){// success
				this.books = [];
				while(getAllBooksRe.data.length > 0){   // 倒叙显示
					this.books.push(getAllBooksRe.data.pop());
				}
				for(let i=0; i<this.books.length; i++) this.booksShowing.push(i);
			}
			else console.log(getAllBooksRe);
		},
		beforeDestroy: function() {
			this.$root.Bus.$off('createBookSuccess')
			this.$root.Bus.$off('editBookSuccess')
		},
	}
</script>

<style scoped>
	.books_root{
		display: flex;
		flex-direction: column;
	}
	/* head */
	.books_root_head{
		display: flex;
		flex-direction: row;
		align-items: baseline;
		padding: 14px 0 34px 0;
		background-color: #f6f6f1;
	}
	.books_root_head_title{
		font-size: 38px;
		min-width: 160px;
		font-weight: bolder;
		font-family: 华文楷体;
		color: rgb(93, 70, 49);
		margin-left: 15%;
	}
	.books_root_head_search{
		display: flex;
		flex-direction: row;
		align-items: center;
		margin-left: 4%;
		width: 60%;
	}
	.books-search_text{
		height: 30px;
		width: 66%;
		padding: 3px 40px 3px 20px;
		text-align: left;
		border-width: 0;
		border-radius: 10px;
		box-shadow: 3px 3px 5px rgba(97, 97, 97, 0.38);
		outline: none;
	}
	.books-search_icon{
		position: relative;
		left: -30px;
		width: 25px;
		height: 25px;
	}
	.books-search_icon:hover {
		transform: scale(1.2);
	}

	/* books_root_body */
	.books_root_body{
		display: flex;
		flex-direction: column;
		margin: 0 10%;
	}
	.books_root_body_book{
		display: flex;
		flex-direction: row;
		padding: 20px 0;
		border-bottom: rgba(132, 132, 132, 0.42) dotted 1px;
	}
	.books-book_image{
		margin-left: 14px;
		box-shadow: 3px 3px 6px #717171;
	}
	.books-book_content{
		display: flex;
		flex-direction: column;
		align-items: start;
		margin-left: 22px;
		width: 100%;
	}
	.books-book-head{
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		width: 100%;
	}
	.books-book-title{
		font-size: 18px;
		margin-top: 4px;
		font-weight: bold;
	}

	.books-book-edit{
		margin-left: auto;
		width: 25px;
		height: 25px;
	}
	.books-book-edit:hover {
		transform: scale(1.2);
	}

	.books-book-delete{
		margin-left: 20px;
		width: 25px;
		height: 25px;
	}
	.books-book-delete:hover {
		transform: scale(1.2);
	}
	.books_root_body_create{
		display: flex;
		position: sticky;
		bottom: 4%;
		margin: 20px -4% 0 auto;
		border-radius: 2px;
		background-color: white;
		opacity: 0.85;
	}
	.books-create_icon{
		width: 52px;
		height: 52px;
	}
	.books-create_icon:hover {
		transform: scale(1.2);
	}

	.books-book-info{
		margin: 16px 0;
		font-size: 14px;
		color: #7d7d7d;
	}
	.books-book-quote{
		margin-top: 16px;
		font-size: 14px;
	}
</style>
