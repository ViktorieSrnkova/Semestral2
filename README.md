# TECHNICKÁ UNIVERZITA V LIBERCI  <br/> Fakulta mechatroniky, informatiky a mezioborových studií #

## Semestrální práce pro předmět ALG2 ##
# Soutěž ve StarCraft2 #

### 1. Zadání ###
Program načte data z textových souborů hráči(informace o hráčích odděleno čárkami), datumy a časy(datumy a startovní časy pro vyřazovací kola skupin) a mapy(seznam map ve hře) a sám rozdělí jednotlivé hráče do skupin po 6ti hráčích. Následně uživatel vybere jednu ze skupin a odehraje s ní vyřazovací zápasy, program mezitím odehraje vyřazovací zápasy se zbytkem skupin. Každá dvojice hráčů hraje 3 hry na 3 různých mapách(hry se v programu odehrávají hodem kostkou programu případně zadáním uživatelova čísla z rozsahu čísel na kostce). První co dosáhne 2 vyhraných map ze 3 postupuje a připisuje se mu jedna výhra. Program nakonec určí ze 3 poražených posledního postupujícího z této skupiny, určuje to podle počtu vyhraných map a pokud se hráči shodují pak hrají proti sobě jednu hru a výherce postupuje. Program dále vypíše seznam postupujících ze všech skupin. Nakonec program vytváří dvojice ze seznamu postupujících opět stejný princip jako ve vyřazovacích zápasech a postupně vypisuje výsledky kol 16ti hráčů, 8mi hráčů, 4hráčů, 2hráčů a nakonec vypíše výherce, při výpisu výherce zazní vybrané audio a dále program vypíše tabulku výherců a peněžních částek co vyhráli. Program ukládá soubory: skupiny, postupující ze skupin, konečné skóre všech hráčů,konečné skóre všech hráčů v binárním souboru a tabulka výherců s jejich výherními částkami. Program se opakuje dokud ho uživatel neukončí potvrzením 0 nebo menšího čísla.

### 2. Návrh řešení ###
### a) Funkční specifikace ###
* Načíst  seznam hráčů
* Setřídit seznam hráčů abecedně podle jména
* Rozdělit hráče do 4 skupin 
* Uložit seznam skupin 
* Rozřazovací kola (16 z 24 postupuje)
    * Vybrat skupinu hráčů
         * Odehrát skupinové zápasy
         * Setřídit hráče sestupně podle počtu vyhraných map
         * Určit 4. postupujícího
    * Vypsat všechny postupující
    * Uložit seznam všech postupujících
* Zápasy o pořadí
    * Setřídit hráče sestupně podle počtu výher
    * Odehrát zápasy o pořadí a vypsat výsledky
    * Vypsat finální skóre všech hráčů (24 hráčů)
    * Vypsat výherce
    * Spustit audio
    * Uložit finální skóre
        * Do textového souboru
        * Do binárního souboru
    * Vypsat pořadní tabulku
    * Uložit pořadní tabulku
   
### b) Popis struktury vstupních a výstupních souborů ###
### Vstupní soubory ###
**Seznam hráčů** : textový soubor obsahující informace oddělené čárkou, obsahuje 6 různých informací na každém řádku, první dva řádky jsou pouze dodatečné informace(název a typy informací)
1. číslo hráče 
2. Národnost
3. Herní rasa
4. Herní jméno
5. Tým
6. Počet EPT bodů (body ze všech předchozích soutěží za daný rok)

**Mapy** : textový soubor obsahující seznam všech dostupných map ze hry pro tuto soutěž, jeden řádek jedna mapa řádek zakončen čárkou, první řádek pouze název souboru 

**Datumy a Časy** : textový soubor obsahující seznam 4 datumů kdy se soutěž koná a jejich startovní časy, data oddělena mezerou

### Výstupní soubory ###
**Seznam skupin** : zformátováno do přehledného výpisu, informace odděleny mezerami, stále 6 informací, před každými 6ti řádky název skupiny, textový soubor

**Všichni postupující** : zformátováno do přehledného výpisu, informace odděleny mezerami, pouze 16 řádků, 10 informací, textový soubor
1. číslo hráče 
2. Národnost
3. Herní rasa
4. Herní jméno
5. Tým
6. Počet EPT bodů (body ze všech předchozích soutěží za daný rok)
7. Počet vyhraných map
8. Počet vyhraných her
9. Datum odehrání zápasů
10. Čas kdy se začaly zápasy hrát 

**Finální výsledky** : zformátováno do přehledného výpisu, informace odděleny mezerami, 24 řádků, 8 informací, textový soubor
1. číslo hráče 
2. Národnost
3. Herní rasa
4. Herní jméno
5. Tým
6. Počet EPT bodů (body ze všech předchozích soutěží za daný rok)
7. Počet vyhraných map
8. Počet vyhraných her

**Finální výsledky v bin** : binární soubor obsahující
1. Počet výher
2. Jméno hráče
3. Rasa ze hry
4. Národnost
5. Tým
6. EPT body
7. Číslo hráče 

**Pořadní tabulka** : zformátovaná ručně vytvořená tabulka obsahující informace
1. Pořadí
2. Vyhraná peněžní částka
3. Jméno hráče

### c) Class Diagram ###
![UML](https://raw.githubusercontent.com/ViktorieSrnkova/Semestral2/master/UML_diagram.png)

### Testování ###
|Číslo testu |Typ testu         |Oblast                        |Vstupní hodnota             | Očekávaný výsledek                                   | skutečný výsledek                                    | prošel(ano/ne)|
|------------|------------------|------------------------------|----------------------------|------------------------------------------------------|------------------------------------------------------|---------------|
|1           |běžná hodnota     |výběr skupiny                 |  D                         | výpis skupiny D                                      | výpis skupiny D                                      |    Ano        |
|2           |běžná hodnota     |výběr dvojice                 |  jména z vybrané skupiny   | výpis dvou hráčů z vybrané skupiny                   | výpis dvou hráčů z vybrané skupiny                   |    Ano        |
|3           |běžná hodnota     |výběr čísla v rozsahu 1-6     |  4                         | porovnání s hodem kostky programu                    | porovnání s hodem kostky programu                    |    Ano        |             
|4           |nepovolený vstup  |výběr dvojice                 |  výběr hráče který už hrál | upozornění uživateli a výzva k zadání jiného jména   | upozornění uživateli a výzva k zadání jiného jména   |    Ano        |                  
|5           |nepovolený vstup  |výběr dvojice                 |  jména z nevybrané skupiny | upozornění uživateli a výzva k zadání jiného jména   | upozornění uživateli a výzva k zadání jiného jména   |    Ano        |                                             
|6           |limitní stav      |výběr čísla v rozsahu 1-6     |  1                         | porovnání s hodem kostky programu                    | porovnání s hodem kostky programu                    |    Ano        |                                                       
|7           |limitní stav      |výběr čísla v rozsahu 1-6     |  6                         | porovnání s hodem kostky programu                    | porovnání s hodem kostky programu                    |    Ano        |               
|8           |nevalidní vstup   |výběr skupiny                 |  f                         | upozornění uživateli a výzva k zadání jiné skupiny   | upozornění uživateli a výzva k zadání jiné skupiny   |    Ano        |                                                      
|9           |nevalidní vstup   |výběr dvojice                 |  neexistující jména        | upozornění uživateli a výzva k zadání jiného jména   | upozornění uživateli a výzva k zadání jiného jména   |    Ano        |                                                                      
|10          |nevalidní vstup   |výběr čísla na konci programu |  K                         | upozornění uživateli a výzva k zadání jiného čísla   | upozornění uživateli a výzva k zadání jiného čísla   |    Ano        |                               

![UML](https://github.com/ViktorieSrnkova/Semestral2/blob/master/Sn%C3%ADmek%20obrazovky%202021-06-13%20165216.png)
![UML](https://github.com/ViktorieSrnkova/Semestral2/blob/master/Sn%C3%ADmek%20obrazovky%202021-06-13%20165236.png)
![UML](https://github.com/ViktorieSrnkova/Semestral2/blob/master/Sn%C3%ADmek%20obrazovky%202021-06-13%20165246.png)
![UML](https://github.com/ViktorieSrnkova/Semestral2/blob/master/Sn%C3%ADmek%20obrazovky%202021-06-13%20165241.png)

### Popis funkce externí kihovny ###
Použila jsem knihovnu :	

sun.audio
  
Jak jsem ji našla: 

hledala sem jednoduchý způsob jak v Javě spustit audio
  
Jak jsem ji použila:	

použila jsem její části AudioPlayer a AudioStream, abych vytvořila metodu která spustí audio
  
Jaké příkazy jsou nezbytné pro její funkčnost:

potřebujeme: FileInputStream(vhodný pro načítání bytů např. obrázku nebo audia), AudioStream (rozpozná byty jako audio), AudioPlayer(dokáže spustit audio), ošetření IOException

Tutoriál k použití této knihovny:
	
https://www.youtube.com/watch?v=3q4f6I5zi2w








