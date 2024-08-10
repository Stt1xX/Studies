<template>
    <div id="task" class="rounded">{{ props.value }} </div>
</template>

<script setup>
    import { defineProps } from 'vue';
    const props = defineProps({
        value : String
    })
</script>

<style scoped>
    #task {
        font-size: 3vh;
        padding: 2vh;
        margin: 0 20vw;
        margin-bottom: 8vh;
        text-align: center;
    }
</style>