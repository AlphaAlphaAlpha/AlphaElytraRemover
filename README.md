## Alpha Elytra Remover - Version 2.5.0
# Beschreibung
Das Alpha Elytra Remover-Plugin für Minecraft hilft dabei, Elytras, die in ItemFrames innerhalb der End Cities platziert werden, zu entfernen. Das Plugin ermöglicht es, das Ablegen von Elytras in ItemFrames zu verhindern, wenn sich Spieler in Welten befinden, die in der Konfiguration angegeben sind. Darüber hinaus kann das Plugin so konfiguriert werden, dass es Elytras in regelmäßigen Abständen entfernt, wobei du volle Kontrolle über das Logging von Chat und Konsole hast.

Wichtig: Dieses Plugin ist besonders nützlich für Serverbetreiber, die verhindern möchten, dass Elytras in den End-Welten unkontrolliert verteilt werden, und für diejenigen, die die Konfiguration flexibel anpassen möchten.

## Funktionen
Automatische Elytra-Entfernung: Entfernt Elytras, die in ItemFrames in den konfigurierten End-Welten platziert wurden.
Verhindern von Elytras im ItemFrame: Spieler können keine Elytras in ItemFrames in den angegebenen Welten ablegen.
Konfigurierbare Intervallsteuerung: Lege fest, in welchen Abständen die Elytras überprüft und entfernt werden sollen.
Chat- und Konsolen-Logging: Steuerbar über die Konfiguration.
/aetoggle console – Aktiviert/Deaktiviert das Konsolen-Logging.
/aetoggle chat – Aktiviert/Deaktiviert das Chat-Logging.
/aetoggle frame off – Deaktiviert das Prevent-Log, wenn Elytras in ItemFrames abgelegt werden.
/aereload – Lädt die Konfiguration neu, ohne den Server neu zu starten.
Benutzerfreundliche Konfiguration: Anpassbare Einstellungen wie Welten, Intervalle und Logging-Optionen.


## Changelog
Version 2.5.0
Neue Konfiguration für prevent-place: Benutzer können jetzt einstellen, ob die Elytras in ItemFrames verhindert werden.
Verbesserte Befehlskontrolle mit /aetoggle für Chat- und Konsolen-Logging.
/aereload-Befehl für einfaches Nachladen der Konfiguration ohne Neustart des Servers.
Mehr Optionen zur Konfiguration des Chat-Loggings und Konsolen-Loggings.
Fehlerbehebung und Optimierung der Elytra-Entfernungsmechanismen.

# Version 2.4.0
Erste Veröffentlichung des Plugins.
Installation
Lade die .jar-Datei herunter: Du kannst die neueste Version des Plugins von GitHub oder deinem bevorzugten Repository herunterladen.
Platzieren der .jar-Datei: Verschiebe die Datei in den plugins-Ordner deines Minecraft-Servers.
Server Neustarten: Starte den Server neu oder führe /reload aus, um das Plugin zu laden.
Konfiguration: Bearbeite die config.yml im Plugin-Ordner, um das Plugin nach deinen Wünschen anzupassen.
Beispiel-Konfiguration
````
# Liste der Welten, in denen Elytras aus ItemFrames entfernt werden
endworlds:
  - world_the_end
  - end_city_world

# Intervall in Ticks (wird alle 'interval-ticks' Ticks wiederholt)
interval-ticks: 6000

# Aktiviert das Konsolen-Logging
console-logging: true

# Aktiviert das Chat-Logging
chat-logging: true

# Nachrichten, die im Chat und in der Konsole angezeigt werden
messages:
  prevent-place: "Du kannst keine Elytra in Itemframes in dieser Welt ablegen!"
  remove-elytra: "Elytras wurden aus ItemFrames entfernt!"
````

Befehle
/aetoggle console – Schaltet das Konsolen-Logging ein/aus.
/aetoggle chat – Schaltet das Chat-Logging ein/aus.
/aetoggle frame off – Deaktiviert das Prevent-Log, wenn Elytras in ItemFrames abgelegt werden.
/aereload – Lädt die Konfiguration neu, ohne den Server neu zu starten.
Permissions
````ae.toggle.console```` – Berechtigung, um das Konsolen-Logging zu aktivieren/deaktivieren.
````ae.toggle.chat```` – Berechtigung, um das Chat-Logging zu aktivieren/deaktivieren.
````ae.toggle.frame```` – Berechtigung, um das Prevent-Log für das Ablegen von Elytras in ItemFrames zu aktivieren/deaktivieren.
Support
Für Unterstützung, Fragen oder Feature-Anfragen kannst du die Issues-Seite des GitHub-Repositories besuchen.
