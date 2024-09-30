<template>
    <Scatter
    id="my-chart-id"
        :data="data"
        :options="options"
    />
</template>



<script setup>
    import { Scatter } from 'vue-chartjs'
    import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, Filler} from 'chart.js'
    import { computed, onBeforeMount } from 'vue';

    onBeforeMount(() => {
        ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, Filler)
    })

    const props = defineProps({
        args_values : {
            type : Object,
            default : {
                euler_result : {
                    x_values : [],
                    y_values : []
                },
                euler_improved_result : {
                    x_values : [], 
                    y_values : []
                },
                miln_result : {
                    x_values : [],
                    y_values : [],
                },
                true_result : {
                    x_values : [],
                    y_values : []
                }
            }
        }
    })

    const euler_color = "#355C7D"
    const euler_improved_color = "#C06C84"
    const miln_color = "#6C5B7B"
    const true_color = "#A3C4BC"

    const data = computed(() => {
        return {    
            datasets: [
                {
                    label: 'Метод Эйлера',
                    data: prepare_points(props.args_values.euler_result.x_values, props.args_values.euler_result.y_values),
                    backgroundColor: euler_color,
                    borderColor: euler_color,
                    tension: 0.4,
                    borderWidth: 2,
                    elements: {
                        point:{
                            radius: 0
                        },
                    },
                },
                {
                    label: 'Метод Эйлера усоверешенствованный',
                    data: prepare_points(props.args_values.euler_improved_result.x_values, props.args_values.euler_improved_result.y_values),
                    backgroundColor: euler_improved_color,
                    borderColor: euler_improved_color,
                    tension: 0.4,
                    borderWidth: 2,
                    elements: {
                        point:{
                            radius: 0
                        },
                    },
                },
                {
                    label: 'Метод Милна',
                    data: prepare_points(props.args_values.miln_result.x_values, props.args_values.miln_result.y_values),
                    backgroundColor: miln_color,
                    borderColor: miln_color,
                    tension: 0.4,
                    borderWidth: 2,
                    elements: {
                        point:{
                            radius: 0
                        },
                    },
                },
                {
                    label: 'Точное значение',
                    data: prepare_points(props.args_values.true_result.x_values, props.args_values.true_result.y_values),
                    backgroundColor: true_color,
                    borderColor: true_color,
                    tension: 0.4,
                    borderWidth: 2,
                    elements: {
                        point:{
                            radius: 0
                        },
                    },
                }
            ],
            
        }
    })

    const options = computed(() => {
        return {
            aspectRatio: 1,
            showLine: true,
            interaction: {
                mode : 'nearest'
            }
        }
    })

    const prepare_points = (x, y) => {
        let ret_arr = []
        for(let i = 0; i < x.length; i++){
            ret_arr.push({x: x[i], y : y[i]})
        }
        return ret_arr
    }
</script>

<style scoped>

</style>