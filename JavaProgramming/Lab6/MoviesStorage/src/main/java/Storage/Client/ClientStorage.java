package Storage.Client;

import Storage.LightAbstractCommand.LightAbstractCommand;
import Storage.LightAbstractCommand.ComplexTypes;
import Storage.LightAbstractCommand.PrimitiveTypes;

import java.util.HashMap;
import java.util.Map;

public class ClientStorage {
        private static final Map<String, LightAbstractCommand> commandStorage = new HashMap<>();

        public Map<String, LightAbstractCommand> getCommandStorage(){
            return commandStorage;
        }
        public ClientStorage(){
            PrimitiveTypes[] zero = new PrimitiveTypes[0];
            ComplexTypes[] zero1 = new ComplexTypes[0];
//            LightAbstractCommand save = new LightAbstractCommand("save", zero, zero1,
//                    "save : сохранить коллекцию в файл");
            LightAbstractCommand add = new LightAbstractCommand("add", zero, new ComplexTypes[]{ComplexTypes.MOVIE},
                    "add {element} : добавить новый элемент в коллекцию");
            LightAbstractCommand update_id = new LightAbstractCommand("update", new PrimitiveTypes[]{PrimitiveTypes.INT}, new ComplexTypes[]{ComplexTypes.MOVIE},
                    "update id {element} : обновить значение элемента коллекции, id которого равен заданному");
            LightAbstractCommand info = new LightAbstractCommand("info", zero, zero1,
                    "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
            LightAbstractCommand show = new LightAbstractCommand("show", zero, zero1,
                    "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
            LightAbstractCommand help = new LightAbstractCommand("help", zero, new ComplexTypes[]{},
                    "help : вывести справку по доступным командам");
            LightAbstractCommand remove_by_id = new LightAbstractCommand("remove_by_id", new PrimitiveTypes[]{PrimitiveTypes.INT}, zero1,
                    "remove_by_id id : удалить элемент из коллекции по его id");
            LightAbstractCommand clear = new LightAbstractCommand("clear", zero, zero1,
                    "clear : очистить коллекцию");
            LightAbstractCommand execute_script = new LightAbstractCommand("execute_script", new PrimitiveTypes[]{PrimitiveTypes.STRING}, zero1,
                    "execute_script file_name : считать и исполнить скрипт из указанного файла. " +
                            "В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
            LightAbstractCommand exit = new LightAbstractCommand("exit", zero, zero1,
                    "exit : завершить программу (без сохранения в файл)");
            LightAbstractCommand remove_head = new LightAbstractCommand("remove_head", zero, zero1,
                    "remove_head : вывести первый элемент коллекции и удалить его");
            LightAbstractCommand add_if_min = new LightAbstractCommand("add_if_min", zero, new ComplexTypes[]{ComplexTypes.MOVIE},
                    "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
            LightAbstractCommand remove_greater = new LightAbstractCommand("remove_greater", zero, new ComplexTypes[]{ComplexTypes.MOVIE},
                    "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный");
            LightAbstractCommand sum_of_oscars_count = new LightAbstractCommand("sum_of_oscars_count", zero, zero1,
                    "sum_of_oscars_count : вывести сумму значений поля oscarsCount для всех элементов коллекции");
            LightAbstractCommand group_counting_by_genre = new LightAbstractCommand("group_counting_by_genre", zero, zero1,
                    "group_counting_by_id : сгруппировать элементы коллекции по значению поля id, вывести количество элементов в каждой группе");
            LightAbstractCommand count_greater_than_height = new LightAbstractCommand("count_greater_than_height", new PrimitiveTypes[]{PrimitiveTypes.FLOAT}, zero1,
                    "count_greater_than_genre genre : вывести количество элементов, значение поля genre которых больше заданного");

            commandStorage.put(add.getName(), add);
            commandStorage.put(update_id.getName(), update_id);
            commandStorage.put(info.getName(), info);
            commandStorage.put(show.getName(), show);
            commandStorage.put(help.getName(), help);
            commandStorage.put(remove_greater.getName(), remove_greater);
            commandStorage.put(remove_by_id.getName(), remove_by_id);
            commandStorage.put(clear.getName(), clear);
            commandStorage.put(execute_script.getName(), execute_script);
            commandStorage.put(exit.getName(), exit);
            commandStorage.put(remove_head.getName(), remove_head);
            commandStorage.put(add_if_min.getName(), add_if_min);
            commandStorage.put(sum_of_oscars_count.getName(), sum_of_oscars_count);
            commandStorage.put(group_counting_by_genre.getName(), group_counting_by_genre);
            commandStorage.put(count_greater_than_height.getName(), count_greater_than_height);
//            commandStorage.put(save.getName(), save);
        }

        public LightAbstractCommand searchCommand(String name){
                return commandStorage.get(name);
        }


}
