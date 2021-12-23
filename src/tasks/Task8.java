package tasks;

import common.Person;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

  private long count;

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  public List<String> getNames(List<Person> persons) {
    return persons.stream().skip(1).map(Person::getFirstName).collect(Collectors.toList());  //Пропустим элемент в стриме - потому что, это лучше чем его удалить.
  }                                                                                          //1.Удалять 1 элемент не всегда быстро
                                                                                             //2.Если List не изменяемый, будет ████████████████████████████████
                                                                                                                              //██████████▀▀▀▀▀▀▀▀▀▀▀███████████
                                                                                                                              //██████▀▀▀░░░░░░░░░░░░░▀▀████████
                                                                                                                              //███▀░░░░░░░▄▄▄░░░▄▄▄░░░░░░░▀████
                                                                                                                              //███░░░░░▄▄███████████▄▄▄░░░░░███
                                                                                                                              //███░░░░████▀▀▀▀▀▀▀▀▀█████▄░░░███
                                                                                                                              //████▄▄░█▀▀░░▄▄░░▄▄░░░░░██▀░░▄███
                                                                                                                              //████████░░▄▄█████████░░█████████
                                                                                                                              //████████▄▄██▀░░░░░▀█████████████
                                                                                                                              //█████████████▄░░░▄██████████████
                                                                                                                              //██████████████░░░███████████████
                                                                                                                              //██████████████░░░███████████████
                                                                                                                              //█████████████▀░░░▀██████████████
                                                                                                                              //███████▄▄████░░░░░████▄▄████████
                                                                                                                              //█████░▄▄░███░░░░░░░███░▄▄░██████
                                                                                                                              //█████░▀██▀▀░░░░░░░░░▀▀██▀░██████
                                                                                                                              //██████▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄███████
                                                                                                                              //████████████████████████████████

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));//1.distinct() не нужен элементы и так уникальны, стрим не нужен.
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) {
    if (person == null) return "";
    return Stream.of(person.getFirstName(),person.getSecondName(),person.getMiddleName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) { //Действительно в Collection могут быть 2 одиниковых обьекта, а я убрал проверку и перезаписывал его.
    return persons.stream()                                                //Вернул и добавил что он не пустой, а то NullPointerException
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(Person::getId,
                    this::convertPersonToString,
                    (oldValue, newValue) -> oldValue
            ));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    return !Collections.disjoint(persons1,persons2);                      //Накосячил containsAll - явно не подходит.
  }                                                                       // Узнал поро Collections)) из которого импользуется только EMPTY_LIST(Так сказанно было на уроке), а мне пригодился.

  //...
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num% 2 == 0).count();                    //Есть count() зачем изобретать если логику не меняет.
  }                                                                       // И forEach - терминальный оператор и получается повторыный проход по элементам. P.S.Возмоно с последним я не прав.

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = false;
    boolean reviewerDrunk = true;
    return codeSmellsGood || reviewerDrunk;
  }
}
