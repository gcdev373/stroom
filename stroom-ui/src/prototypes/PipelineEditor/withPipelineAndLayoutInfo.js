/*
 * Copyright 2018 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { compose } from 'recompose';
import { connect } from 'react-redux';
import { getPipelineLayoutInformation } from './pipelineUtils';

/**
 * This is a Higher Order Component
 * https://reactjs.org/docs/higher-order-components.html
 *
 * It provides the pipeline layout information by using the pipeline provided by withPipeline
 *
 * @param {React.Component} WrappedComponent
 */
export function withPipelineAndLayoutInfo() {
  return (WrappedComponent) => {
    const WithPipelineAndLayoutInfo = class extends Component {
      static propTypes = {
        pipelineId: PropTypes.string.isRequired,
        asTree: PropTypes.object.isRequired,
      };

      state = {
        layoutInformation: undefined,
      };

      static getDerivedStateFromProps(nextProps, prevState) {
        let layoutInformation;
        if (!!nextProps.pipeline && !!nextProps.pipeline.asTree) {
          layoutInformation = getPipelineLayoutInformation(nextProps.pipeline.asTree);
        }

        return {
          layoutInformation,
        };
      }

      render() {
        if (this.state.layoutInformation) {
          return <WrappedComponent {...this.state} {...this.props} />;
        }
        return <span>awaiting pipeline tree state</span>;
      }
    };

    return compose(connect((state, props) => ({
      pipeline: state.pipelines[props.pipelineId],
    })))(WithPipelineAndLayoutInfo);
  };
}
