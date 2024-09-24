<template>
        <div id="result" class="rounded">
            <div>Результат:</div> <button type="button" id="download">Скачать</button>
            <p v-for="(value, key) in props.value"> {{ key }}<br> {{ value }} </p>
        </div>
</template>

<script setup>
    import { defineProps } from 'vue';
    const props = defineProps({
        value : Object
    })
</script>

<style scoped>
    #result {
        display: flex;
        justify-content: space-around;
        align-items: center;
        flex-wrap: wrap;
        padding: 2vh;
        margin: 8vh 20vw 0vh 20vw;
    }
    
    #result p{
        font-size: 3vh;
        text-align: center;
        width: 100%;
    }

    #result div{
        text-align: center;
        width: 70%;
    }

    #download {
        font-size: 1.5vw;
        padding: 1vh 2vw;
    }
</style>