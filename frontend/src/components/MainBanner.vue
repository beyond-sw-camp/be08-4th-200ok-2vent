<template>
    <Carousel :autoplay="3000" :wrap-around="true" :transition="1000" :pauseAutoplayOnHover="true"
            @mouseover="isHovered = true" @mouseout="isHovered = false">
        <Slide v-for="(image, i) in images" :key="i">
            <div class="carousel__item" >
                <img :src="getImageUrl(image.image)" class="img">
                
            </div>
        </Slide>

        <template #addons>
            <!-- <Pagination /> -->
            <div v-show="isHovered">
                <Navigation />
            </div>
        </template>
    </Carousel>
</template>

<script>
    import { defineComponent } from 'vue'
    import { Carousel, Pagination, Slide, Navigation } from 'vue3-carousel'

    import 'vue3-carousel/dist/carousel.css'
    import data from '../assets/image.js'

export default defineComponent({
    name: 'MainBanner',
        components: {
            Carousel,
            Slide,
            Pagination,
            Navigation
        },
        data() {
            return {
                images : data,
                isHovered : false,
            }
        },
        methods:{
            getImageUrl(url) {
                return new URL(`${url}`, import.meta.url).href;
            },
        },
    })
</script>

<style>
.carousel__item {
    /* min-height: 200px; */
    height: 350px;
    width: 100%;
    background-color: var(--vc-clr-primary);
    color: var(--vc-clr-white);
    /* font-size: 20px; */
    /* border-radius: 8px; */
    display: flex;
    justify-content: center;
    align-items: center;
}

.carousel__prev,
.carousel__next {
    box-sizing: content-box;
    border: 1px solid white;
    /* display: none; */
}

.img{
    width: 100%;
}
</style>