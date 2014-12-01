Exercise 4 "Tamagochi"
======================

Task -1: Build and run the project yourself
-------------------------------------------

1. [Download a copy](https://github.com/H4ssi/exercise-4-tamagochi/archive/master.zip)
2. Run gradle (it will create a [eclipse project](http://eclipse.org/) for you)
   * on Windows just double-click `gradlew.bat` 
   * on Linux or MacOS run `./gradlew` 
3. Import the project into eclipse
   * via: `File` - `Import...` - `General` - `Existing Projects into Workspace`

Hint: This works for [IntelliJ IDEA](https://www.jetbrains.com/idea/) as well (use: `File` - `Open...`), in case you prefer it over eclipse.

Task 0: Verstehe den Ausgangs-Code
----------------------------------

#### Zur Aufgabe

Sinn und Zweck dieser Übung ist das erstellen eines "Tamagochis".
Das sind einfache Simulationen von Lebewesen - üblicherweise hat man die in Form eines kleinen Geräts mit Bildschirm herumgetragen - Für unsere Zwecke bauen wir eines für den Computer.

Der prinzipielle Ablauf des Programm ist folgendermaßen:

Wenn man das Programm zum ersten Mal startet, dann wird man nach dem Namen des Tamagochis gefragt.
Sobald man diesen angegebn hat, beginnt auch schon die Simulation.

Das Tamagochi macht dabei dann übliche Tätigkeiten, passend zu seinem Thema:

Ein Schwein würde z.B. regelmäßig grunzen und sich im Dreck suhlen. Dann hätte es noch gerne was zum essen und schlafen möchte es auch noch.

Dabei kann dem Schwein nicht nur zuschauen sondern auch aktiv eingreifen. Z.b. könnte man es füttern.
Dafür müssen dem Tamagochi Befehle erteilt werden, die einfach als Text übertragen werden:

Z.b. 'Iss 3 Karotten' Könnte bedeuten, dass man dem Schwein 3 Karotten zum essen gibt.

Am besten wäre es, wenn man im Spiel mehrere Sachen erreich könnte: Z.b. wäre es erstrebenswert dass das Schwein super zufrieden ist. Andererseits wäre es traurig, wenn es vernachlässigt wird. Oder es rennt vielleicht sogar weg, weil es so schlecht behandelt wurde...

#### Zum Code

Es gibt ein Interface 'TamagochiLogic' das implementiert werden muss. In diesem kann das Verhalten des Tamagochis komplett programmiert werden.

Zur Interaktion gibt es auch noch das Interface 'Engine' damit kann man z.B. beschreiben was gerade mit dem Schwein passiert.

Schauen Sie sich daher folgende Pakete an: 'pos1_2ahif.ex_4_tamagochi' ist das Paket in dem Sie Ihren Code unterbringen - Dabei vor allem in der Klasse 'MyTamagochiLogic'.

Das Paket 'pos1_2ahif.ex_4_tamagochi.engine.api' beschreibt die Schnittstelle zu der Tamagochi Engine.

Die anderen Pakete - insbesondere 'pos1_2ahif.ex_4_tamagochi.engine.impl' brauchen Sie _nicht_ anzuschauen!

#### Wie zeige ich etwas am Bildschirm an?

Verwenden Sie dazu `Engine.render` um ein Bild ihres Tamagochis anzuzeigen, `Engine.status` um den Status ihres Tamagochis anzuzeigen `Engine.log` um eine generelle Nachricht zu schreiben.

Zur Darstellung verwenden Sie "Ascii-Art". Das ist wenn man mit herkömmlicher Schrift, Bilder darstellt. Sie können dabei das Schriftzeichen, die Vordergrundfarbe (das ist die Farbe des Zeichen selber) sowie die Hintergrundfarbe bestimmen.

Der Einfachheithalber, bestimmen Sie all diese Information nur mit Strings:

##### Wir zeichnen einen Baum

Zuerst brauchen wir die Form des Baums, angenommen das Bild ist 4 Zeichen breit und 6 Zeichen hoch, dann malen wir unseren Baum so:

```
 @@ 
@@@@
@@@@
 HH 
 HH 
""""
```

Sehen Sie? @ sind die Blätter, H ist der Stamm und " ist ein bisschen Gras darunter,

Mit ein bisschen Farbe wird das noch überzeugender, schauen Sie sich diese Palette an:

![palette](https://raw.github.com/H4ssi/exercise-4-tamagochi/res/caca-palette.png)

Wir machen damit die Baumkrone grün, den Stamm braun und das Gras grün, dafür verwenden wir so einen String

```
 II 
IIII
IIII
 dd 
 dd 
JJJJ
```

Und im Hintergrund machen wir den Himmel blau, den Rest halten wir in ähnlichen Farbtönen:

```
oiio
iiii
iiii
oDDo
oDDo
DDDD
```

Damit bekommen wir so ein Bild:

![palette](https://raw.github.com/H4ssi/exercise-4-tamagochi/res/baum.png)

Der vollständige JavaCode schaut dann so aus

```
String symbols = " @@ \n" +
        "@@@@\n" +
        "@@@@\n" +
        " HH \n" +
        " HH \n" +
        "\"\"\"\"";
String foreground = " II \n" +
        "IIII\n" +
        "IIII\n" +
        " dd \n" +
        " dd \n" +
        "JJJJ";
String background = "oiio\n" +
        "iiii\n" +
        "iiii\n" +
        "oDDo\n" +
        "oDDo\n" +
        "DDDD";

Frame f = engine.createGraphicsFrame().fromStrings(
        symbols,
        foreground,
        background);

engine.render(f);
```

Kopieren Sie das z.B. testweise in die `init` Methode,

##### Text schreiben geht auch anders

Wenn wir nur Text schreiben wollen, brauchen wir normalerweise nicht so viele Farben. Deswegen gibt es hier eine andere Möglichkeit:

Stellen Sie sich vor, wir wollen folgenden Text schreiben:

```
This is a very long text - and I cannot decide between red and green. But I want this sentence to be blue.
```

Wir wollen dabei den ersten Teil rot und grün haben, den zweiten Teil nur blau. Wir beschreiben das folgendermaßen

```
1. Segment:
  Text: "This is a very long text - and I cannot decide between red and green. "
  Foreground: "ai" # helles rot und helles grün
  Background: "BJ" # dunkles rot und dunkles grün
2. Segment:
  Text: "But I want this sentence to be blue."
  Foreground: "oqsq" # mehrere varianten von blau
  Background: null   # nimm eine standard farbe
```

Damit bekämen wir folgenden Text (die Farben werden immer wieder wiederholt):

![palette](https://raw.github.com/H4ssi/exercise-4-tamagochi/res/text.png)

In Java schaut das so aus:

```
Frame t = engine.createLogFrame().fromSegments(
        /* 1. Segment */
        new FrameSegment(
                "This is a very long text - and I cannot decide between red and green. ",
                "ai",
                "BJ"),
        /* 2. Segment */
        new FrameSegment(
                "But I want this sentence to be blue.",
                "oqsq",
                null));
engine.log(t);
```

Das können Sie wieder in die `init` Methode kopieren, um das auszuprobieren.

### Task 1: Ihr Tier

Überlegen Sie sich ein Tier und machen Sie davon ein Bild und zeigen dieses an.

Für Ihr Bild haben Sie 16 Zeichen in der Breite und 8 Zeichen in der Höhe zur Verfügung.

### Task 2: Macht Sachen

Überlegen Sie sich, was ihr Tier regelmäßig so macht. Lassen Sie Ihr Tier alle 10 Sekunden eine gewisse Tätigkeit machen und merken Sie sich wie oft ihr Tier das schon gemacht hat!

Geben Sie im Log aus, wenn so eine Tätigkeit gerade passiert (z.B. "Grunz!")

Zeigen Sie die Anzahl im Status an. Für den Status haben Sie 16 Zeichen in der Breite zur Verfügung.

### Task 3: Gehorcht aufs Wort

Programmieren Sie einen Befehl an ihr Tier folgendermaßen:

```Mach X Y Mal```

In unserem Beispiel von dem Schwein wäre das z.B.

```Mach grunz 2000 Mal```

Damit Ihr Schwein 2000x grunzt.

Verwenden Sie dafür die Scanner Klasse.

Wenn der Befehl nicht richtig eingegeben wurde, soll Ihr Tier zu erkennen geben, dass es nicht verstanden hat.
Wenn der Befehl richtig eingegeben wurde, soll Ihr Tier gehorchen - Vergessen Sie nicht auf dem Log auszugeben was passiert und den Status zu aktualisieren!

### Task 4: Kann man zwischenzeitlich ausschalten

Implementieren Sie die Methoden `load` und `store` um den Status ihres Tiers zu speichern.
Wenn Sie das Programm beenden, wird automatisch `store` aufgerufen.
Wenn das Programm gestartet wird, wird automatisch `load` aufgerufen.

Sie brauchen nur mehr diese 2 Methoden richtig implementieren.

Überlegen Sie sich ein sinnvolles Dateiformat.

Wenn Sie einen Fehler gemacht haben, schauen Sie auf die Kommandozeile, dort steht, in welche Datei die Daten gespeichert werden. Sehen Sie dort nach, ob alles passt. Im Zweifelsfall können Sie diese Datei immer wieder löschen, wenn diese einmal falsch angelegt wurde.
