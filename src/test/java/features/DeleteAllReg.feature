#Author: hsaraowgi@cvent.com
@tag
Feature: Bulk Delete All Reg on a Croud Compass Event
  I want to delete all the reg invitations on a croud compass event

  @tag1
  Scenario: Delete All Reg
    Given I have the list of top 100 Invitations
    And All 100 invitations saved locally
    When i call delete function for all 100 Invitations individually
    But There are still invitations remaining so call all steps again to delete next 100 and do that till all are deleted
    Then Trying to get all invitaions again will respond with blank array but 200 response

  @tag2
  Scenario: Delete All Sessions
    Given I have the list of top 100 sessions
    And All 100 sessions saved locally
    When i call delete function for all 100 sessions individually
    But There are still sessions remaining so call all steps again to delete next 100 and do that till all are deleted
    Then Trying to get all sessions again will respond with blank array but 200 response
