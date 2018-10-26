#!/bin/bash
# Jenkins
#
# Lancement/arrêt de Jenkins

case $1 in
    "start")
        /bin/bash /home/alex/dev/jenkins/launch.sh
    ;;
    "stop")
        /bin/bash /home/alex/dev/jenkins/stop.sh
    ;;
    *)
	echo "Commande inconnue!"
    ;;
esac
exit 0
