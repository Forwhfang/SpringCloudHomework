<template>
	<div class="myRate_root">
		<div class="myRate_body">
			<!-- stars -->
			<div class="myRate_body_stars">
				<div class="myRate-star" v-for="(v, i) in new Array(count)" :key="i">
					<img :style="starStyle" src="../assets/books/star.png" />
				</div>
			</div>
			<!-- 背景色板 -->
			<div :style="starsBackgroundStyle" class="myRate_body_starsBackground"></div>

			<!-- rate text -->
			<div :style="textStyle" class="myRate_body_text">{{ rate * totalScore }}</div>
		</div>
	</div>
</template>

<script>
	export default {
		name: "myRate",
		props: {
			/* 必填 */
			/* 当前评分率 */
			rate:{
				type: Number,
				required: true
			},

			/* 选填 */
			/* 每个star的宽、高，单位为px */
			size:{
				type: Number,
				default: 20
			},
			/* star的数量 */
			count: {
				type: Number,
				default: 5
			},
			/* 总分 */
			totalScore:{
				type: Number,
				default: 10
			},
			/* 现在用图片实现，无法指定color */
			// color: {
			// 	type: String,
			// 	default:
			// }
		},
		computed:{
			starStyle(){
				return {
					width: this.size + 'px',
					height: this.size + 'px'
				}
			},
			starsBackgroundStyle(){
				return {
					width: this.size * this.count * this.rate + 'px',
					height: this.size + 'px'
				}
			},
			textStyle(){
				return {
					fontSize: Math.ceil((this.size)*0.75) + 'px'
				}
			}
		}
	}
</script>

<style scoped>
	.myRate_root{
		display: flex;
	}
	.myRate_body{
		display: flex;
		flex-direction: row;
		align-items: center;
	}
	.myRate_body_stars{
		display: flex;
		flex-direction: row;
		align-items: center;
		z-index: 2;
	}
	.myRate-star{
		display: flex;
	}
	.myRate_body_starsBackground{
		position: absolute;
		z-index: 1;
		background-color: #FFAF35;
	}
	.myRate_body_text{
		margin: auto 0 0 5px;
		color: #FFAF35;
	}
</style>
