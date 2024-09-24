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
                lagrange_method : {
                    points : {
                        x : [],
                        y : []
                    }
                },
                newton_separated_method : {
                    points : {
                        x : [],
                        y : []
                    }
                },
                newton_finite_method : {
                    points : {
                        x : [],
                        y : []
                    }
                },
                true_value : {
                    points : {
                        x : [],
                        y : []
                    }
                },
                interpol_points : {
                    x : [],
                    y : []
                }
            }
        }
    })

    const interpol_point_color = "#355C7D"
    const lagrange_color = "#C06C84"
    const newton_separete_color = "#6C5B7B"
    const newton_finite_color = "#A3C4BC"
    const true_function_color = "#BFD7B5"

    const data = computed(() => {
        return {    
            datasets: [
                {
                    label: 'Точки интерполяции',
                    data: prepare_points(props.args_values.interpol_points.x, props.args_values.interpol_points.y),
                    backgroundColor: interpol_point_color,
                    borderColor: interpol_point_color,
                    tension: 0.4,
                    borderWidth: 0,
                    elements: {
                        point:{
                            radius: 4
                        },
                    },
                },
                {
                    label: 'Лагранж',
                    data: prepare_points(props.args_values.lagrange_method.points.x, props.args_values.lagrange_method.points.y),
                    backgroundColor: lagrange_color,
                    borderColor: lagrange_color,
                    tension: 0.4,
                    borderWidth: 2,
                    elements: {
                        point:{
                            radius: 0
                        },
                    },
                },
                {
                    label: 'Разделенные разности',
                    data: prepare_points(props.args_values.newton_separated_method.points.x, props.args_values.newton_separated_method.points.y),
                    backgroundColor: newton_separete_color,
                    borderColor: newton_separete_color,
                    tension: 0.4,
                    borderWidth: 2,
                    elements: {
                        point:{
                            radius: 0
                        },
                    },
                },
                {
                    label: 'Конечные разности',
                    data: prepare_points(props.args_values.newton_finite_method.points.x, props.args_values.newton_finite_method.points.y),
                    backgroundColor: newton_finite_color,
                    borderColor: newton_finite_color,
                    tension: 0.4,
                    borderWidth: 2,
                    elements: {
                        point:{
                            radius: 0
                        },
                    },
                },
                {
                    label: 'Истинная функция',
                    data: prepare_points(props.args_values.true_value.points.x, props.args_values.true_value.points.y),
                    backgroundColor: true_function_color,
                    borderColor: true_function_color,
                    tension: 0.4,
                    borderWidth: 2,
                    elements: {
                        point:{
                            radius: 0
                        },
                    },
                },
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