<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.canvas.Canvas?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.control.Tab?>
<?import javafx.scene.control.TabPane?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.BorderPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<BorderPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="458.0" prefWidth="644.0" stylesheets="@styles.css" xmlns="http://javafx.com/javafx/25" xmlns:fx="http://javafx.com/fxml/1" fx:controller="controller.MainController">
   <top>
      <VBox prefHeight="121.0" prefWidth="600.0" styleClass="header-bar" stylesheets="@styles.css" BorderPane.alignment="CENTER">
         <children>
            <HBox prefHeight="100.0" prefWidth="200.0" spacing="16.0" stylesheets="@styles.css">
               <children>
                  <Label prefHeight="51.0" prefWidth="600.0" styleClass="header-badge" stylesheets="@styles.css" text="PG-01">
                     <font>
                        <Font size="18.0" />
                     </font>
                  </Label>
                  <VBox prefHeight="100.0" prefWidth="1013.0">
                     <children>
                        <Label prefHeight="17.0" prefWidth="389.0" styleClass="header-title" stylesheets="@styles.css" text="Recursividad Aplicada">
                           <font>
                              <Font size="24.0" />
                           </font>
                        </Label>
                        <Label styleClass="header-sub" stylesheets="@styles.css" text="IF-3001 Algoritmos y Estructuras de datos">
                           <font>
                              <Font size="24.0" />
                           </font>
                        </Label>
                     </children>
                  </VBox>
               </children>
            </HBox>
         </children>
      </VBox>
   </top>
   <center>
      <TabPane prefHeight="200.0" prefWidth="200.0" styleClass="main-tabs" stylesheets="@styles.css" tabClosingPolicy="UNAVAILABLE" BorderPane.alignment="CENTER">
        <tabs>
          <Tab text="Factorial">
               <content>
                  <SplitPane dividerPositions="0.5" prefHeight="200.0" prefWidth="200.0">
                     <items>
                        <VBox prefHeight="200.0" prefWidth="100.0" spacing="8.0" styleClass="panel">
                           <children>
                              <Label styleClass="panel-title" text="Árbol de llamadas recursivas" />
                              <Canvas fx:id="canvasTree" height="200.0" styleClass="tree-canvas" width="200.0" />
                           </children>
                        </VBox>
                        <VBox prefHeight="281.0" prefWidth="305.0" spacing="10.0" styleClass="panel" />
                     </items>
                  </SplitPane>
               </content>
            </Tab>
          <Tab text="Fibonacci" />
            <Tab text="Grafico">
              <content>
                <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="180.0" prefWidth="200.0" />
              </content>
            </Tab>
        </tabs>
      </TabPane>
   </center>
</BorderPane>
