package com.javaFXApplication.controllers;

import com.javaFXApplication.dao.UserDao;
import com.javaFXApplication.entities.User;
import com.javaFXApplication.utils.Password;
import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.Optional;
import java.util.logging.Logger;

/**     Main Controller
 *        @author Adam Combs
 */
public class MainController {

          private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

          @FXML Slider opacitySlider;
          @FXML AnchorPane topMenu;
          @FXML AnchorPane loginPane;
          @FXML AnchorPane registerPane;
          @FXML AnchorPane mainPane;
          @FXML AnchorPane nav1Pane;
          @FXML AnchorPane nav2Pane;
          @FXML TextField registerUsername;
          @FXML TextField registerPassword1;
          @FXML TextField registerPassword2;
          @FXML TextField loginUsername;
          @FXML TextField loginPassword;
          @FXML ImageView menuImage;
          @FXML ImageView maxRestoreImage;
          @FXML Label loginErrorMessage;
          @FXML Label userLabel;
          @FXML Label registerErrorMessage;
          @FXML Label navLogout;
          @FXML Label navMain;
          @FXML Label navOne;
          @FXML Label navTwo;
          @FXML ProgressIndicator progressIndicator;

          private Stage mainStage;
          private boolean maximizedFlag = false;
          private boolean topMenuFlag = false;

          private EventHandler<MouseEvent> loginEvent = new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent event) {
                              changePane(loginPane);
                    }
          };

          public void initialize(){
                    changePane(mainPane);
                    initSlider();
                    initEvents();
                    navMain.setId("nav-selected");
                    menuToggle();
          }

          public void initEvents(){
                    userLabel.setOnMouseClicked(loginEvent);
          }

          private void initSlider() {
                    opacitySlider.setMin(0.25);
                    opacitySlider.setMax(1.0);
                    opacitySlider.setValue(1.0);
                    opacitySlider.valueProperty().addListener((observable, oldValue, newValue) -> mainStage.setOpacity(newValue.doubleValue()));
          }

          @FXML
          private void navMain(){
                   changePane(mainPane);
                    clearNavIds();
                    navMain.setId("nav-selected");
          }

          @FXML
          private void navOne(){
                    changePane(nav1Pane);
                    clearNavIds();
                    navOne.setId("nav-selected");
          }

          @FXML
          private void navTwo(){
                    changePane(nav2Pane);
                    clearNavIds();
                    navTwo.setId("nav-selected");
          }

          private void clearNavIds(){
                    navMain.setId("");
                    navOne.setId("");
                    navTwo.setId("");
          }

          @FXML
          private void navLogout(){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Logout");
                    ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Stay Logged In");
                    alert.initStyle(StageStyle.TRANSPARENT);
                    alert.setTitle("Logout Confirmation");
                    alert.setContentText("Are you sure you want to log out?");
                    alert.initOwner(mainStage);
                    alert.setHeaderText(null);
                    alert.setGraphic(null);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                              userLabel.setText("Login");
                              userLabel.setCursor(Cursor.HAND);
                              userLabel.setOnMouseClicked(loginEvent);
                              navLogout.setVisible(false);
                              changePane(mainPane);
                    } else {

                    }
          }

          @FXML
          private void menuToggle(){
                    final Timeline timeline = new Timeline();

                    int endValue = topMenuFlag==true ? -80 : 80;
                    topMenuFlag = topMenuFlag==true ? false : true;
                    if (topMenuFlag){
                              menuImage.setImage(new Image(getClass().getResource("/images/baseline_arrow_drop_up_white_48dp.png").toString()));
                    } else {
                              menuImage.setImage(new Image(getClass().getResource("/images/baseline_arrow_drop_down_white_48dp.png").toString()));
                    }

                    final KeyValue keyValue = new KeyValue(topMenu.translateYProperty(), endValue);
                    final KeyFrame keyFrame = new KeyFrame(Duration.millis(500), keyValue);
                    timeline.getKeyFrames().add(keyFrame);
                    timeline.setCycleCount(1);
                    timeline.play();
          }

          @FXML
          private void maxRestoreWindow(){
                    if (maximizedFlag == false) {
                              maxRestoreImage.setImage(new Image(getClass().getResource("/images/sharp_flip_to_back_white_48dp.png").toString()));
                              mainStage.setMaximized(true);
                              maximizedFlag = true;
                    } else {
                              maxRestoreImage.setImage(new Image(getClass().getResource("/images/baseline_maximize_white_48dp.png").toString()));
                              mainStage.setMaximized(false);
                              maximizedFlag = false;
                    }
          }

          @FXML
          private void openLogin(){
                    clearForm(loginPane);
                    loginErrorMessage.setText("");
                    changePane(loginPane);
                    if (topMenuFlag==true){ menuToggle(); }
          }

          @FXML
          private void closeHand(){
                    opacitySlider.setCursor(Cursor.CLOSED_HAND);
          }

          @FXML
          private void openHand(){
                    opacitySlider.setCursor(Cursor.OPEN_HAND);
          }

          @FXML
          private void openRegistration(){
                    clearForm(registerPane);
                    registerErrorMessage.setText("");
                    changePane(registerPane);
                    if (topMenuFlag==true){ menuToggle(); }
          }

          @FXML
          private void submitRegistration(){
                    if (registerUsername.getText().equals("")
                            || registerPassword1.getText().equals("")
                            || registerPassword2.getText().equals("")){
                              registerErrorMessage.setText("No fields can be blank.");
                    } else if  (!registerPassword1.getText().equals(registerPassword2.getText())){
                              registerErrorMessage.setText("Passwords do not match. ");
                    } else {
                              User user = UserDao.findUserByUsername(registerUsername.getText());
                              if (user.getUsername() != null) {
                                        registerErrorMessage.setText("Username already exists.");
                              } else {
                                        String salt = Password.getSalt(30);
                                        String securePassword = Password.generateSecurePassword(registerPassword1.getText(), salt);
                                        user = new User(registerUsername.getText(), securePassword, salt);
                                        UserDao.saveUser(user);
                                        registerErrorMessage.setText("");
                                        userLabel.setOnMouseClicked(null);
                                        changePane(mainPane);
                              }
                    }
          }

          @FXML
          private void cancelRegistration(){
                    changePane(mainPane);
          }

          @FXML
          private void submitLogin(){
//                    progressIndicator.setVisible(true);
                    if (loginUsername.getText().equals("") || loginPassword.getText().equals("")){
                              loginErrorMessage.setText("No fields can be blank.");
                    } else {
                              LOGGER.info("Username: " + loginUsername.getText());
                              User user = UserDao.findUserByUsername(loginUsername.getText());
                              if (user.getUsername()==null) {
                                        loginErrorMessage.setText("Username does not exist.");
                              } else {
                                        String securePassword = user.getPassword();
                                        String salt = user.getSalt();
                                        boolean passwordsMatch = Password.verifyUserPassword(loginPassword.getText(), securePassword, salt);
                                        if (passwordsMatch) {
                                                  userLabel.setText(user.getUsername());
                                                  userLabel.setOnMouseClicked(null);
                                                  userLabel.setCursor(Cursor.DEFAULT);
                                                  navLogout.setVisible(true);
                                                  changePane(mainPane);
                                        } else {
                                                  loginErrorMessage.setText("Username/Password combination is incorrect.  Please login again.");
                                        }
                              }
                    }
//                    progressIndicator.setVisible(false);
          }

          @FXML
          private void cancelLogin(){
                    changePane(mainPane);
          }

          private void clearForm(AnchorPane pane){
                    ObservableList<Node> nodeList = pane.getChildren();
                    for (Node node: nodeList){
                              if (node instanceof TextField){
                                        ((TextField) node).clear();
                              }
                    }
          }

          public void setStage(Stage stage){
                    mainStage = stage;
          }

          @FXML
          private void closeWindow(){
                    mainStage.close();
          }

          @FXML
          private void minimizeWindow(){
                    mainStage.setIconified(true);
          }

          private void changePane(AnchorPane openPane){
                    loginPane.setVisible(false);
                    registerPane.setVisible(false);
                    mainPane.setVisible(false);
                    nav1Pane.setVisible(false);
                    nav2Pane.setVisible(false);
                    openPane.setVisible(true);
          }
}
