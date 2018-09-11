import React from 'react';
import PropTypes from 'prop-types';

import { compose, withProps, withHandlers } from 'recompose';
import { connect } from 'react-redux';
import { Field, reduxForm } from 'redux-form';

import { Header, Form, Button } from 'semantic-ui-react';
import { InputField } from 'react-semantic-redux-form';

import ThemedModal from 'components/ThemedModal';
import { actionCreators } from './redux';

import { uniqueElementName } from './pipelineUtils';
import { required, minLength2 } from 'lib/reduxFormUtils';

const { pipelineElementAddConfirmed, pipelineElementAddCancelled } = actionCreators;

const enhance = compose(
  connect(
    ({ form, pipelineEditor: { pipelineStates } }, { pipelineId }) => {
      const pipelineState = pipelineStates[pipelineId];
      let initialValues;

      const { pendingNewElement } = pipelineState;
      if (pendingNewElement) {
        initialValues = {
          name: pendingNewElement.elementDefinition.type,
        };
      }

      return {
        // state
        newElementForm: form.newElementName,
        pendingNewElement,
        pipelineState,
        initialValues,
      };
    },
    { pipelineElementAddConfirmed, pipelineElementAddCancelled },
  ),
  reduxForm({
    form: 'newElementName',
    // We're re-using the same form for each element's modal so we need to permit reinitialization when using the initialValues prop
    enableReinitialize: true,
    touchOnChange: true,
  }),
  // Properties from owner
  withHandlers({
    // from withNewElementDefinition in owner // Redux form
    onConfirmNewElement: ({
      pipelineElementAddConfirmed,
      pipelineId,
      newElementForm: {
        values: { name },
      },
      reset,
    }) => () => {
      pipelineElementAddConfirmed(pipelineId, name);
      reset();
    },
    onCancelNewElement: ({ pipelineElementAddCancelled, reset, pipelineId }) => () => {
      pipelineElementAddCancelled(pipelineId);
      reset();
    },
  }),
  withProps(({ invalid, submitting, pendingNewElement }) => ({
    submitDisabled: invalid || submitting,
    isOpen: !!pendingNewElement,
  })),
);

const AddElementModal = ({
  pipelineState: { pipeline },
  isOpen,
  submitDisabled,
  onConfirmNewElement,
  onCancelNewElement,
}) => (
  <ThemedModal
    size="tiny"
    isOpen={isOpen}
    onClose={onCancelNewElement}
    dimmer="inverted"
    header={<Header content="Add New Element" />}
    content={
      <Form id="newElementForm">
        <Form.Field>
          <label>Name</label>
          <Field
            name="name"
            component={InputField}
            type="text"
            placeholder="Name"
            validate={[required, minLength2, uniqueElementName(pipeline)]}
            autoFocus
          />
        </Form.Field>
      </Form>
    }
    actions={
      <React.Fragment>
        <Button
          positive
          content="Submit"
          disabled={submitDisabled}
          onClick={onConfirmNewElement}
          form="newElementForm"
        />
        <Button negative content="Cancel" onClick={onCancelNewElement} />
      </React.Fragment>
    }
  />
);

AddElementModal.propTypes = {
  pipelineId: PropTypes.string.isRequired,
};

export default enhance(AddElementModal);
