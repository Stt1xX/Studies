<template>
    <div id="result" class="rounded">
        <div id="header">
            <div>Результат:</div> 
            <button class="arrow" @click="dec_list()">
                <svg width="64px" height="64px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M6 12H18M6 12L11 7M6 12L11 17" stroke="#6e5b7b" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>
            </button>
            <button class="arrow" @click="inc_list()">
                <svg width="64px" height="64px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M6 12H18M18 12L13 7M18 12L13 17" stroke="#6e5b7b" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>
            </button> 
            <button type="button" id="download">Скачать</button>
        </div>
        <div id="error-div" v-if="props.args_values.status == ResponseCode.ERROR">
            <p class="data"> {{ props.args_values.error_message.header }}<br> {{ props.args_values.error_message.value }}</p>
        </div>
        <div id="resut-div" v-if="props.args_values.status == ResponseCode.OK">
            <p class="data">
                {{ current_displaying.header }}<br>
                {{ current_displaying.message }}
            </p>
            <table>
                <tbody>
                    <tr>
                        <th>
                            №
                        </th>
                        <th>
                            x<sub>i</sub>
                        </th>
                        <th>
                            y<sub>i</sub>
                        </th>
                    </tr>
                    <tr v-for="i in current_displaying.x_values_noda.length">
                        <td>
                            {{ i }}
                        </td>
                        <td>
                            {{ Math.round(current_displaying.x_values_noda[i - 1] * 1000) / 1000 }}
                              <!-- {{current_displaying.x_values_noda[i - 1]}} -->
                        </td>
                        <td>
                            {{ Math.round(current_displaying.y_values_noda[i - 1] * 1000) / 1000 }}
                              <!-- {{ current_displaying.y_values_noda[i - 1] }} -->
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        
    </div>
</template>

<script setup>
import { defineProps, ref, computed } from 'vue';

const current_list = ref(1)

const props = defineProps({
    args_values : {
        type: Object,
        default : { }
    }
})

let current_displaying = computed(() => {
    let displayed_props = [props.args_values.euler_result, props.args_values.euler_improved_result, props.args_values.miln_result, props.args_values.true_result] 
    return displayed_props[current_list.value - 1]
})

const ResponseCode = {
        ERROR: 0,
        OK: 1,
}

const inc_list = () => {
    current_list.value = (current_list.value) % 4 + 1
}
    
const dec_list = () => {
    current_list.value = (current_list.value + 2) % 4 + 1
}

</script>

<style scoped>
    #result {
        padding: 2vh;
        display: inline-block;
        margin-top: 8vh;
        min-width: 60vw;
    }

    #header {
        width: 100%;
        display: flex;
        justify-content: space-around;
        align-items: center;
    }

    .data, .tables {
        font-size: 1.5vw;
        text-align: center;
    }

    #download {
        font-size: 1.5vw;
        padding: 1vh 2vw;
    }

    table {
        border-collapse: collapse; 
        width: 100%;
        font-size: 1.5vw;
    }

    th, td {
        border: 2px solid #6e5b7b; /* Граница вокруг ячеек */
    }

    .arrow {
        transition: 0.3s;
        background-color: bisque;
        border-width: 0;
    }

    .arrow:hover {
        transform: scale(1.2);
        cursor: pointer;
        background-color: bisque;
        border-width: 0;
    }
</style>