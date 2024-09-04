<template>
    <div>
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Previous"
                        @click.prevent="emit('change-page', currentPage - 1)">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>

                <li v-for="page in generateSequence(startPage, endPage)" :key="page" class="page-item">
                    <a class="page-link" :class="{active: currentPage === page}" href="#"
                        @click.prevent="emit('change-page', page)">
                        {{ page }}
                    </a>
                </li>

                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Next"
                        @click.prevent="emit('change-page', currentPage + 1)">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</template>


<script setup>
    const props = defineProps({
        currentPage: {
            type: Number,
            required:true
        },
        startPage: {
            type: Number,
            required:true
        },
        endPage: {
            type: Number,
            required:true
        }
    });

    const emit = defineEmits(['change-page']);

    const generateSequence = (startNumber, endNumber) => {
        let sequence = [];

        for (let i = startNumber; i <= endNumber; i++ ) {
            sequence.push(i);
        }

        return sequence;
    };

</script>

<style lang="scss" scoped>

</style>