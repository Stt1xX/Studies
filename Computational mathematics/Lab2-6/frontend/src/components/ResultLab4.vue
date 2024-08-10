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
        <div class="data" v-if="props.linear != undefined && current_list == 1">
            <p> Линейная функция (ax + b)</p>
            <p> {{ linear.status }}</p>
            <table class="tables">
                <tbody>
                    <tr>
                        <td>
                            i
                        </td>
                        <td v-for="n in general.x_values.length">
                            {{ n }}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            x
                        </td>
                        <td v-for="x in general.x_values">
                            {{ x }}
                        </td>
                    </tr>   
                    <tr>
                        <td>
                            y
                        </td>
                        <td v-for="y in general.y_values">
                            {{ y }}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            ф
                        </td>
                        <td v-for="line_value in linear.values">
                            {{ line_value }}
                        </td>
                    </tr>
                    <tr>    
                        <td>
                            &#949;
                        </td>
                        <td v-for="difference in linear.differences">
                            {{ difference }}
                        </td>
                    </tr>
                </tbody>
            </table>
            <p>Среднеквадратичное отклонение: {{ linear.deviation }}</p>
            <p>a: {{ linear.a }} b: {{ linear.b }}</p>
            <p>Коэффициент корреляции Пирсона: {{ linear.pirson }} </p>
            <p>Коэффициент детерминации: {{ linear.reliability }} </p>
        </div>
        <div class="data" v-if="props.quadratic != undefined && current_list == 2">
            <p> Полиномиальная функция 2-й степени (ax<sup>2</sup>+bx+c)</p>
            <p> {{ quadratic.status }}</p>
            <table class="tables">
                <tbody>
                    <tr>
                        <td>
                            i
                        </td>
                        <td v-for="n in general.x_values.length">
                            {{ n }}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            x
                        </td>
                        <td v-for="x in general.x_values">
                            {{ x }}
                        </td>
                    </tr>   
                    <tr>
                        <td>
                            y
                        </td>
                        <td v-for="y in general.y_values">
                            {{ y }}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            ф
                        </td>
                        <td v-for="line_value in quadratic.values">
                            {{ line_value }}
                        </td>
                    </tr>
                    <tr>    
                        <td>
                            &#949;
                        </td>
                        <td v-for="difference in quadratic.differences">
                            {{ difference }}
                        </td>
                    </tr>
                </tbody>
            </table>
            <p>Среднеквадратичное отклонение: {{ quadratic.deviation }}</p>
            <p>a: {{ quadratic.a }} b: {{ quadratic.b }} c: {{ quadratic.c }}</p>
            <p>Коэффициент детерминации: {{ quadratic.reliability }} </p>
        </div>
        <div class="data" v-if="props.cubic != undefined && current_list == 3">
            <p> Полиномиальная функция 3-й степени (ax<sup>3</sup>+bx<sup>2</sup>+cx+d)</p>
            <p> {{ cubic.status }}</p>
            <table class="tables">
                <tbody>
                    <tr>
                        <td>
                            i
                        </td>
                        <td v-for="n in general.x_values.length">
                            {{ n }}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            x
                        </td>
                        <td v-for="x in general.x_values">
                            {{ x }}
                        </td>
                    </tr>   
                    <tr>
                        <td>
                            y
                        </td>
                        <td v-for="y in general.y_values">
                            {{ y }}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            ф
                        </td>
                        <td v-for="line_value in cubic.values">
                            {{ line_value }}
                        </td>
                    </tr>
                    <tr>    
                        <td>
                            &#949;
                        </td>
                        <td v-for="difference in cubic.differences">
                            {{ difference }}
                        </td>
                    </tr>
                </tbody>
            </table>
            <p>Среднеквадратичное отклонение: {{ cubic.deviation }}</p>
            <p>a: {{ cubic.a }} b: {{ cubic.b }} c: {{ cubic.c }} d: {{ cubic.d }}</p>
            <p>Коэффициент детерминации: {{ cubic.reliability }} </p>
        </div>
        <div class="data" v-if="props.exp != undefined && current_list == 4">
            <p> Экспоненциальная функция (ae<sup>bx</sup>)</p>
            <div style="width : 50vw; display: inline-block;" v-if="props.exp.error != undefined">
                <p> {{ exp.error }} </p>
            </div>
            <div v-else>
                <p> {{ exp.status }}</p>
                <table class="tables">
                    <tbody>
                        <tr>
                            <td>
                                i
                            </td>
                            <td v-for="n in general.x_values.length">
                                {{ n }}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                x
                            </td>
                            <td v-for="x in general.x_values">
                                {{ x }}
                            </td>
                        </tr>   
                        <tr>
                            <td>
                                y
                            </td>
                            <td v-for="y in general.y_values">
                                {{ y }}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                ф
                            </td>
                            <td v-for="line_value in exp.values">
                                {{ line_value }}
                            </td>
                        </tr>
                        <tr>    
                            <td>
                                &#949;
                            </td>
                            <td v-for="difference in exp.differences">
                                {{ difference }}
                            </td>
                        </tr>
                    </tbody>
                </table>
                <p>Среднеквадратичное отклонение: {{ exp.deviation }}</p>
                <p>a: {{ exp.a }} b: {{ exp.b }}</p>
                <p>Коэффициент детерминации: {{ exp.reliability }} </p>
            </div>
        </div>
        <div class="data" v-if="props.log != undefined && current_list == 5">
            <p> Логарифмическая функция (aln(x)+b)</p>
            <div style="width : 50vw; display: inline-block;" v-if="props.log.error != undefined">
                <p> {{ log.error }} </p>
            </div>
            <div v-else>
                <p> {{ log.status }}</p>
                <table class="tables">
                    <tbody>
                        <tr>
                            <td>
                                i
                            </td>
                            <td v-for="n in general.x_values.length">
                                {{ n }}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                x
                            </td>
                            <td v-for="x in general.x_values">
                                {{ x }}
                            </td>
                        </tr>   
                        <tr>
                            <td>
                                y
                            </td>
                            <td v-for="y in general.y_values">
                                {{ y }}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                ф
                            </td>
                            <td v-for="line_value in log.values">
                                {{ line_value }}
                            </td>
                        </tr>
                        <tr>    
                            <td>
                                &#949;
                            </td>
                            <td v-for="difference in log.differences">
                                {{ difference }}
                            </td>
                        </tr>
                    </tbody>
                </table>
                <p>Среднеквадратичное отклонение: {{ log.deviation }}</p>
                <p>a: {{ log.a }} b: {{ log.b }}</p>
                <p>Коэффициент детерминации: {{ log.reliability }} </p>
            </div>
        </div>
        <div class="data" v-if="props.degree != undefined && current_list == 6">
            <p> Степенная функция (ax<sup>b</sup>) </p>
            <div style="width : 50vw; display: inline-block;" v-if="props.degree.error != undefined">
                <p> {{ degree.error }} </p>
            </div>
            <div v-else>
                <p> {{ degree.status }}</p>
                <table class="tables">
                    <tbody>
                        <tr>
                            <td>
                                i
                            </td>
                            <td v-for="n in general.x_values.length">
                                {{ n }}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                x
                            </td>
                            <td v-for="x in general.x_values">
                                {{ x }}
                            </td>
                        </tr>   
                        <tr>
                            <td>
                                y
                            </td>
                            <td v-for="y in general.y_values">
                                {{ y }}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                ф
                            </td>
                            <td v-for="line_value in degree.values">
                                {{ line_value }}
                            </td>
                        </tr>
                        <tr>    
                            <td>
                                &#949;
                            </td>
                            <td v-for="difference in degree.differences">
                                {{ difference }}
                            </td>
                        </tr>
                    </tbody>
                </table>
                <p>Среднеквадратичное отклонение: {{ degree.deviation }}</p>
                <p>a: {{ degree.a }} b: {{ degree.b }}</p>
                <p>Коэффициент детерминации: {{ degree.reliability }} </p>
            </div>
        </div>
    </div>
</template>

<script setup>

import { defineProps, ref, defineEmits } from 'vue';

    let current_list = ref(1)

    const props = defineProps({
        linear : Object,
        general : Object,
        quadratic : Object,
        cubic : Object,
        degree : Object,
        exp : Object,
        log : Object
    })

    const emit = defineEmits(['change-list'])

    const inc_list = () => {
        current_list.value = (current_list.value) % 6 + 1
        emit('change-list', current_list.value)
    }

    const dec_list = () => {
        current_list.value = (current_list.value + 4) % 6 + 1
        emit('change-list', current_list.value)
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

    .data p, .tables {
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