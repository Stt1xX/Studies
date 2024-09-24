    <template>
    <div id="result" class="rounded">
        <div id="header">
            <div>Результат:</div> <button type="button" id="download">Скачать</button>
        </div>
        <p class="data" v-for="(value, key) in props.args_values"> {{ value.header }}<br> {{ value.value }} </p>
        <p class="data" v-if="props.diffs_table != undefined">  {{ props.diffs_table.header }}</p>
        <table class="tables" v-if="props.diffs_table != undefined">
            <tbody>
                <tr>
                    <th>
                        №
                    </th>
                    <th>
                        x<sub>i</sub>
                    </th>
                    <th v-for="i in props.diffs_table.table.length">
                        &Delta;y<sup v-if="i != 1">{{ i }}</sup><sub>i</sub>
                    </th>
                </tr>
                <tr v-for="(row, row_index) in props.diffs_table.table">
                    <td>
                        {{ row_index }}
                    </td>
                    <td v-for="(cell, cell_index) in row">
                        <span v-if="cell_index < row.length - row_index">{{ cell }}</span>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script setup>
import { defineProps } from 'vue';
const props = defineProps({
    args_values : Object,
    diffs_table : Object
})
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