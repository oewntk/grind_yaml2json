<p align="center">
<img width="256" height="256" src="images/oewntk.png" alt="OEWNTK">
</p>
<p align="center">
<img width="150" src="images/mavencentral.png" alt="MavenCentral">
</p>

# Open English Wordnet YAML-to-JSON grinder

This library reads a model from YAML files and writes it to JSON format.

Project [grind_yaml2json](https://github.com/oewntk/grind_yaml2json)

See also [model](https://github.com/oewntk/model/blob/master/README.md).

See also [fromyaml](https://github.com/oewntk/fromyaml/blob/master/README.md).

See also [tojson](https://github.com/oewntk/tojson/blob/master/README.md).

See also [oewntk](https://github.com/oewntk)
and [globalwordnet/english-wordnet](https://github.com/globalwordnet/english-wordnet).

## Dataflow

![Dataflow](images/dataflow_yaml2json.png  "Dataflow")

This library reads from the OEWN distribution YAML files and other YAML files that contain extra data.

This output conforms to the **JSON** standards.

## Command line

`grind.sh [YAML] [YAML2] [JSON]`

grinds the JSON database

*where*

[YAML] directory where OEWN distribution YAML files are

[YAML2] directory where extra YAML files are

[JSON] path to output JSON file

## Maven Central

		<groupId>io.github.oewntk</groupId>
		<artifactId>yaml2json</artifactId>
		<version>2.2.0</version>

## Dependencies

![Dependencies](images/grind-yaml2json.png  "Dataflow")
