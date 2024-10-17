README ETAPA 2

Explicare Cod:

Inainte de toate, mentionez ca am folosit scheletul oferit de echipa pentru etapa a doua.

Am facut unele modificari in command runner, unde am adaugat comenzile de la noi. 
In acelasi timp comenzile din, au fost adaugate in meniul din main.

SpecialUsers este o clasa de useri cu abilitati speciale(artisti si hosti). Acestia pot fi
adaugati, scosi, sau sa faca ceva in functie de tipul de user. Artistii pot adauga albume, 
evenimente si merch, in timp ce un host poate adauga anunturi si podcasturi.(artistii 
si hostii au fiecare cate o clasa cu metodele si campurile proprii, iar anunuturile,
evenimentele si merchurile sunt clase interne pentru fiecare user special). Artistul contine
un array de albume, iar hostul detine un array de podcasturi. Acestea din urma sunt adaugate 
si in admin, pentru a avea o evidenta mai buna(Adminul a fost facut Singleton).

UserPages, ca si SpecialPages sunt interfetele pentru pagini. Acestea implementeaza clasele 
pentru crearea paginilor userilor de default, ale artistilor sau ale hostilor. Fiecare clasa
speciala are cate o metoda care construieste pagina local si o si returneaza

Comenzi de afisare(afiseaza exact ceea ce se cere):
-getTop5Albums (ale unui artist) ,getTop5Artists, getAllUsers, getOnlineUsers

Comenzi de adaugare/stergere entitati de tip non user(Useri sunt inclusi in Admin):
-AddUser, DeleteUser

Comenzi de adaugare/stergere entitati de tip non user(sunt incluse in userul aferent, dar si 
in admin, pnetru evidenta):
-AddAlbum,RemoveAlbum (adauga/scot un album la un artist)
-AddEvent ,RemoveEvent (adauga/scot un eveniment din artist)
-AddMerch (adauga merchul nou al unui artist)
-AddPodcast ,RemovePodcast(adauga un podcast la un host), 
AddAnnouncement, RemoveAnnouncement

Comenzi de tip comportament useri:
-SwitchConnectionStatus(seteaza un uer pe online/offline. Statusul offline 
afecteaza anumite comenzi noi)

Folosire ChatGpt:
-Sortatrile din getTop5Albums/getTop5Artists(Admin: 201, 221):
-Eliminarile de elemente facute pe liste cu ajutorul clasei Iterator din:
    host(Host:198-204);
    artist(Artistt:246-252, 254-260, 263-268, 314-320, 322-330, 362-369);
    user(User: 584-590);


